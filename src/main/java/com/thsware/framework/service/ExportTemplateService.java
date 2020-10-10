package com.thsware.framework.service;


import com.thsware.framework.domain.*;
import com.thsware.framework.repository.*;
import com.thsware.framework.service.dto.ZjProjectDTO;
import com.thsware.framework.service.dto.ZjPublishDTO;
import com.thsware.framework.service.dto.ZjSpecialtyAuditerDTO;
import com.thsware.framework.service.dto.ZjSpecialtyDTO;
import com.thsware.framework.service.mapper.ZjProjectMapper;
import com.thsware.framework.service.mapper.ZjSpecialtyMapper;
import com.thsware.framework.web.client.CscggClient;
import com.thsware.framework.web.client.ThsWFClient;
import com.thsware.framework.web.client.ThsadminClient;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service("exportTemplateService")
@Transactional(readOnly = true)
public class ExportTemplateService {

    private final Logger log = LoggerFactory.getLogger(ExportTemplateService.class);

    @Autowired
    private ZjProjectService zjProjectService;

    @Autowired
    private ZjSpecialtyService zjSpecialtyService;

    @Autowired
    private ZjMemberService zjMemberService;

    @Autowired
    private ZjPublishService zjPublishService;

    private ZjSpecialtyMapper zjSpecialtyMapper;

    @Autowired
    private ZjSpecialtyAuditerService zjSpecialtyAuditerService;

    @Autowired
    CscggClient cscggClient;

    @Autowired
    ThsadminClient thsadminClient;

    @Autowired
    ThsWFClient thsWFClient;

    public ExportTemplateService(ZjSpecialtyMapper zjSpecialtyMapper) {
        this.zjSpecialtyMapper = zjSpecialtyMapper;
    }

    /**
     * 对象转Map方法
     *
     * @param obj
     * @return map
     */
    public Map<String, String> objectConversionMap(Object obj) {
        Map<String, String> map = new HashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(obj);
                if (o != null) {
                    if (o.getClass().equals(Instant.class)) {
                        Instant time = (Instant) o;
                        Date date = Date.from(time);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String temp_str = sdf.format(date);
                        map.put("${" + field.getName() + "}", temp_str);
                    } else {
                        map.put("${" + field.getName() + "}", o.toString());
                    }
                } else {
                    map.put("${" + field.getName() + "}", " ");
                }
                field.setAccessible(flag);
            } catch (Exception e) {
                log.error("对象转map方法异常", e);
            }
        }

        return map;
    }

    /**
     * 金额转换大写方法
     *
     * @param money
     * @return
     */
    public String numberToCN(Double money) {
        money = money*10000;
        String fraction[] = {"角", "分"};
        String digit[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String unit[][] = {{"元", "万", "亿"}, {"", "拾", "佰", "仟"}};
        String head = money < 0 ? "负" : "";
        money = Math.abs(money);

        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int) (Math.floor(money * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if (s.length() < 1) {
            s = "整";
        }
        int integerPart = (int) Math.floor(money);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p = "";
            for (int j = 0; j < unit[1].length && money > 0; j++) {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }


    /**
     * 日期类型转换方法
     */
    public static String getUpperDate(String date) {
        char[] upper = "零一二三四五六七八九十".toCharArray();
        if (date == null) return null;
        //非数字的都去掉
        date = date.replaceAll("\\D", "");
        if (date.length() != 6) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(upper[Integer.parseInt(date.substring(i, i + 1))]);
        }
        sb.append("年");
        int month = Integer.parseInt(date.substring(4, 6));
        if (month <= 10) {
            sb.append(upper[month]);
        } else {
            sb.append("十").append(upper[month % 10]);
        }
        sb.append("月");
        return sb.toString();
    }

    /**
     * 对象转换
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, String> getZjProjectInfoById(String id) {
        log.debug("Request to get ZjProject : {}", id);
        Optional<ZjProjectDTO> zjProjectDTO = zjProjectService.findOne(id);
        return objectConversionMap(zjProjectDTO.get());
    }


    /**
     *获取字典项名称
     * @param type
     * @return
     */
    public String getType(String type){
            String[] arr = type.split(",");
            StringBuffer typeList = new StringBuffer();
            List<Map<String, String>> listmap = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                Map map1 = new HashMap<>();
                map1.put("itemValue1.equals",arr[i]);
                Object obj = thsadminClient.getZdByType(map1).getBody();
                List<Map<String, String>> list = (List<Map<String, String>>) obj;
                if (list != null && list.size() > 0) {
                    listmap.add(list.get(0));
                }
            }
            if(listmap != null && listmap.size() > 0){
                for(int i = 0; i < listmap.size(); i++){
                    typeList.append(listmap.get(i).get("itemText"));
                    typeList.append(",");
                }
            }
            if (typeList != null && typeList.length() > 0) {
                String types = typeList.toString();
                return types;
            }
            return " ";
        }


    /**
     * 替换通用占位符并下载
     */
    public void replaceAndGenerateWord(String srcPath, String destPath, Map<String, String> map) {

        //项目负责人
        String projectManagerName = map.get("${projectManagerName}");
        if(!StringUtils.isEmpty(projectManagerName) && !" ".equals(projectManagerName)){
            map.put("${projectManager}",projectManagerName);
        }else {
            map.put("${projectManager}"," ");
        }

//        String busiType = map.get("${busiType}");
//        if(!StringUtils.isEmpty(busiType) && !" ".equals(busiType)){
//            map.put("${busiType}",getType(busiType));
//        }else {
//            map.put("${busiType}"," ");
//        }

//        String implementUnit = map.get("${implementUnit}");
//        if (!StringUtils.isEmpty(implementUnit) && !" ".equals(implementUnit)) {
//            map.put("${implementUnit}",getType(implementUnit));
//        } else {
//            map.put("${implementUnit}"," ");
//        }



        String delegateUnit = map.get("${delegateUnit}");
        if(!StringUtils.isEmpty(delegateUnit) && !" ".equals(delegateUnit)){
            String[] arr3 = delegateUnit.split(",");
            StringBuffer name = new StringBuffer();
            List<Map<String,String>> listb = new ArrayList<>();
            for (int i = 0; i < arr3.length; i++) {
                Map<String ,String> map2 = cscggClient.getWtdwById(arr3[i]).getBody();
                listb.add(map2);
            }
            for (int j = 0; j < listb.size(); j++) {
                name.append(listb.get(j).get("name"));
                name.append(" ");
            }
            String delegateUnit1 = name.toString();
            map.put("${delegateUnit}",delegateUnit1);
        }else {
            map.put("${delegateUnit}"," ");
        }

        String[] sp = srcPath.split("\\.");
        String[] dp = destPath.split("\\.");
        if ((sp.length > 0) && (dp.length > 0)) {
            if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
                try {
                    XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
                    // 替换段落中的指定文字
                    Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
                    while (itPara.hasNext()) {
                        XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                        Set<String> set = map.keySet();
                        Iterator<String> iterator = set.iterator();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            List<XWPFRun> run = paragraph.getRuns();
                            for (int i = 0; i < run.size(); i++) {
                                if (run.get(i).getText(run.get(i).getTextPosition()) != null && run.get(i).getText(run.get(i).getTextPosition()).equals(key)) {
                                    run.get(i).setText(map.get(key), 0);
                                }
                            }
                        }
                    }
                    // 替换表格中的指定文字
                    Iterator<XWPFTable> itTable = document.getTablesIterator();
                    while (itTable.hasNext()) {
                        XWPFTable table = (XWPFTable) itTable.next();
                        int rcount = table.getNumberOfRows();
                        for (int i = 0; i < rcount; i++) {
                            XWPFTableRow row = table.getRow(i);
                            List<XWPFTableCell> cells = row.getTableCells();
                            for (XWPFTableCell cell : cells) {
                                String cellTextString = cell.getText();
                                for (Map.Entry<String, String> e : map.entrySet()) {
                                    if (cellTextString.contains(e.getKey())) {
                                        cellTextString = cellTextString.replace(e.getKey(), e.getValue());
                                        cell.removeParagraph(0);
                                        cell.setText(cellTextString);
                                    }
                                }
                            }
                        }
                    }
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(destPath);
                    document.write(outStream);
                    outStream.close();
                } catch (Exception e) {
                    log.error("docx通用占位符内部异常",e);
                }

            } else if ((sp[sp.length - 1].equalsIgnoreCase("doc"))
                && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {
                HWPFDocument document = null;
                try {
                    document = new HWPFDocument(new FileInputStream(srcPath));
                    Range range = document.getRange();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        range.replaceText(entry.getKey(), entry.getValue());
                    }
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(destPath);
                    document.write(outStream);
                    outStream.close();
                } catch (Exception e) {
                    log.error("doc通用占位符内部异常",e);
                }
            }
        }
    }

    /**
     * 工程概预结算编制审核定案汇总表.docx 个性化处理方法
     *
     * @param destPath
     * @param list
     */
    @Transactional(readOnly = true)
    public void findGcgyjsbzshdaById(String destPath, List<ZjSpecialty> list) {
        try {
            FileInputStream fileInputStream = new FileInputStream(destPath);
            XWPFDocument document = new XWPFDocument(fileInputStream);
            List<XWPFTable> tables = document.getTables();
            XWPFTable table = tables.get(0);
            XWPFTableRow header = table.getRow(5);
            for (int i = 0; i < list.size(); i++) {
                table.addRow(header, 5);
            }
            fileInputStream.close();
            OutputStream output = new FileOutputStream(destPath);
            document.write(output);
            output.close();
            FileInputStream fileInputStream1 = new FileInputStream(destPath);
            XWPFDocument document1 = new XWPFDocument(fileInputStream1);
            List<XWPFTable> tables1 = document1.getTables();
            XWPFTable table1 = tables1.get(0);
            Integer number = 0;
            double sdje = 0;
            for (int i = 5; i < 5 + list.size(); i++) {
                XWPFTableRow row = table1.getRow(i);
                number++;
                row.getCell(0).setText(number.toString());
                if (list.get(i - 5).getEngineeringName() != null) {
                    row.getCell(1).setText(list.get(i - 5).getEngineeringName());
                }
                if (list.get(i - 5).getSubmitMoney() != null) {
                    row.getCell(2).setText(list.get(i - 5).getSubmitMoney().toString());
                }
                if (list.get(i - 5).getApprovalMoney() != null) {
                    row.getCell(3).setText(list.get(i - 5).getApprovalMoney().toString());
                    sdje += list.get(i - 5).getApprovalMoney();
                }
                double hzjs = 0;
                if (list.get(i - 5).getApprovalMoney() != null && list.get(i - 5).getSubmitMoney() != null) {
                    hzjs = list.get(i - 5).getApprovalMoney() - list.get(i - 5).getSubmitMoney();
                } else if (list.get(i - 5).getSubmitMoney() != null) {
                    hzjs = 0 - list.get(i - 5).getSubmitMoney();
                } else if (list.get(i - 5).getApprovalMoney() != null) {
                    hzjs = list.get(i - 5).getApprovalMoney();
                }
                if (hzjs != 0) {
                    Double hjs = Math.abs(hzjs);
                    if (hzjs > 0) {
                        row.getCell(5).setText(hzjs + "");
                    } else {
                        row.getCell(4).setText(hjs.toString());
                    }
                }
            }
            XWPFTableRow row = table1.getRow(7 + list.size());
            if (sdje != 0) {
                row.getCell(1).setText(numberToCN(sdje));
            }
            OutputStream output1 = new FileOutputStream(destPath);
            document1.write(output1);
            output1.close();
        } catch (Exception e) {
            log.error("工程概预结算编制审核定案汇总表.docx 个性化处理方法异常",e);
        }
    }

    /**
     * 工程预算（标底）、投标报价编制汇总表.docx 个性化方法
     *
     * @param destPath
     * @param list
     */
    public void findGcysbdtbbjbzhzbById(String destPath, List<ZjSpecialty> list) {
        try {
            FileInputStream fileInputStream = new FileInputStream(destPath);
            XWPFDocument document = new XWPFDocument(fileInputStream);
            List<XWPFTable> tables = document.getTables();
            XWPFTable table = tables.get(0);
            XWPFTableRow header = table.getRow(3);
            for (int i = 0; i < list.size(); i++) {
                table.addRow(header, 3);
            }
            fileInputStream.close();
            OutputStream output = new FileOutputStream(destPath);
            document.write(output);
            output.close();
            FileInputStream fileInputStream1 = new FileInputStream(destPath);
            XWPFDocument document1 = new XWPFDocument(fileInputStream1);
            List<XWPFTable> tables1 = document1.getTables();
            XWPFTable table1 = tables1.get(0);
            Integer number = 0;
            for (int i = 3; i < 3 + list.size(); i++) {
                number++;
                XWPFTableRow row = table1.getRow(i);
                row.getCell(0).setText(number.toString());
                if (list.get(i - 3).getEngineeringName() != null) {
                    row.getCell(1).setText(list.get(i - 3).getEngineeringName());
                }
            }
            OutputStream output1 = new FileOutputStream(destPath);
            document1.write(output1);
            output1.close();
        } catch (Exception e) {
            log.error("工程预算（标底）、投标报价编制汇总表.docx 个性化方法异常",e);
        }
    }

    /**
     * 项目任务单.docx 个性化方法
     *
     * @param destPath
     * @param list
     */
    public void findXmrwdById(String destPath, List<String> list) {
        try {
            FileInputStream fileInputStream = new FileInputStream(destPath);
            XWPFDocument document = new XWPFDocument(fileInputStream);
            List<XWPFTable> tables = document.getTables();
            XWPFTable table = tables.get(0);
            XWPFTableRow header = table.getRow(9);
            for (int i = 0; i < list.size(); i++) {
                table.addRow(header, 9);
            }
            OutputStream output = new FileOutputStream(destPath);
            document.write(output);
            output.close();
            FileInputStream fileInputStream1 = new FileInputStream(destPath);
            XWPFDocument document1 = new XWPFDocument(fileInputStream1);
            List<XWPFTable> tables1 = document1.getTables();
            XWPFTable table1 = tables1.get(0);
            for (int i = 9; i < 9 + list.size(); i++) {
                XWPFTableRow row = table1.getRow(i);
                row.getCell(1).setText(list.get(i - 9));
            }
            OutputStream output1 = new FileOutputStream(destPath);
            document1.write(output1);
            output1.close();
        } catch (Exception e) {
            log.error("项目任务单.docx 个性化方法异常",e);
        }
    }



    /**
     * 仅替换通用占位符
     *
     * @param id
     */
    public void getGGById(String srcPath, String destPath, String id) {
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        replaceAndGenerateWord(srcPath, destPath, map);
    }




    /**
     * 工程概预结算编制审核定案汇总表.docx
     *
     * @param id
     */
    public void getGcgyjsbzshdaById(String srcPath, String destPath, String id) {
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        String busiType = map.get("${busiType}");
        if(!StringUtils.isEmpty(busiType) && !" ".equals(busiType)){
            if (busiType.equals("编制类-概算")) {
                map.put("${title}","概算编制");
            } else if (busiType.equals("编制类-施工图预算")) {
                map.put("${title}","预算编制");
            } else if (busiType.equals("编制类-竣工结算")) {
                map.put("${title}","结算编制");
            } else if (busiType.equals("审核类-概算")) {
                map.put("${title}","概算审核");
            } else if (busiType.equals("审核类-施工图预算")) {
                map.put("${title}","预算审核");
            } else if (busiType.equals("审核类-竣工结算")) {
                map.put("${title}","结算审核");
            } else if (busiType.equals("全过程造价类")) {
                map.put("${title}","全过程造价类");
            } else if (busiType.equals("经济纠纷鉴定和仲裁咨询")) {
                map.put("${title}","经济纠纷鉴定和仲裁咨询");
            } else {
                map.put("${title}","");
            }
        }else {
            map.put("${title}","");
        }
//        String specialty = map.get("${specialty}");
//        if(!StringUtils.isEmpty(specialty) && !" ".equals(specialty)){
//            map.put("${specialty}",getType(specialty));
//        } else {
//            map.put("${specialty}"," ");
//        }
        replaceAndGenerateWord(srcPath, destPath, map);
        List<ZjSpecialty> list = new ArrayList<>();
        if (ids.length > 1) {
            list = zjSpecialtyMapper.toEntity(zjSpecialtyService.findAllByZjPublishId(ids[1]));
        } else {
            list = zjSpecialtyService.findAllByZjProjectId(id);
        }
        if (list != null && list.size() > 0) {
            findGcgyjsbzshdaById(destPath, list);
        }
    }

    /**
     * 封面、签署页、封底.docx
     *
     * @param id
     */
    public void getFmqsyfdById(String srcPath, String destPath,  String id) {
        log.debug("进入getFmqsyfdById·····························");

        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
//        if (ids.length > 1) {
//            Optional<ZjPublishDTO> zjPublishDTO = zjPublishService.findOne(ids[1]);
//            if(zjPublishDTO.get().getPublishTime() != null){
//                String cbPublishDate = zjPublishDTO.get().getPublishTime().toString();
//                String cbDate = cbPublishDate.substring(0, 7);
//                map.put("${publishDate}", getUpperDate(cbDate));
//            } else {
//                map.put("${publishDate}", "年  月");
//            }
//        } else {
            String publishDate = map.get("${publishDate}");
            if (!StringUtils.isEmpty(publishDate) && !" ".equals(publishDate)) {
                String cbDate = publishDate.substring(0, 7);
                map.put("${publishDate}", getUpperDate(cbDate));
            } else {
                map.put("${publishDate}", "年  月");
            }
//        }
        replaceAndGenerateWord(srcPath, destPath, map);
    }

    /**
     * 工程预算（标底）、投标报价编制汇总表.docx
     *
     * @param id
     */
    public void getGcysbdtbbjbzhzbById(String srcPath, String destPath, String id) {
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        replaceAndGenerateWord(srcPath, destPath, map);
        List<ZjSpecialty> zjSpecialtyList = new ArrayList<>();
        if (ids.length > 1) {
            zjSpecialtyList = zjSpecialtyMapper.toEntity(zjSpecialtyService.findAllByZjPublishId(ids[1]));
        } else {
            zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(id);
        }
        if (zjSpecialtyList != null && zjSpecialtyList.size() > 0) {
            findGcysbdtbbjbzhzbById(destPath, zjSpecialtyList);
        }

    }

    /**
     * 合同审批单.docx
     *
     * @param id
     */
    public void getHtspdById(String srcPath, String destPath, String id) {
        Map<String, String> map = getZjProjectInfoById(id);
        String contractId = map.get("${contractId}");
        if (!StringUtils.isEmpty(contractId) && !" ".equals(contractId)) {
            Map<String, String> map1 = cscggClient.getHtById(contractId).getBody();
            String serviceType = map1.get("serviceType");
            if (!StringUtils.isEmpty(serviceType) && !" ".equals(serviceType)) {
                map.put("${serviceType}",serviceType);
            }else {
                map.put("${serviceType}"," ");

            }
        }
        replaceAndGenerateWord(srcPath, destPath, map);
    }

    /**
     * 项目立项审批单.docx
     *
     * @param id
     */
    public void getXmlxspdById(String srcPath, String destPath, String id) {
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        String contractId = map.get("${contractId}");
        if (!StringUtils.isEmpty(contractId) && !" ".equals(contractId)) {
            Map<String, String> map1 = cscggClient.getHtById(contractId).getBody();
            String contractMoney = map1.get("contractMoney");
            if (!StringUtils.isEmpty(contractMoney) && !"0.0".equals(contractMoney)) {
                map.put("${contractMoney}", map1.get("contractMoney"));
            }else {
                map.put("${contractMoney}"," ");
            }
        } else {
            map.put("${contractMoney}"," ");
        }

        String projectType = map.get("${projectType}");
        if(!StringUtils.isEmpty(projectType) && !" ".equals(projectType)){
            map.put("${projectType}",getType(projectType));
        }else {
            map.put("${projectType}"," ");
        }
//        String projectSource = map.get("${projectSource}");
//        if(!StringUtils.isEmpty(projectSource) && !" ".equals(projectSource)){
//            map.put("${projectSource}",getType(projectSource));
//        }else {
//            map.put("${projectSource}"," ");
//
//        }
        replaceAndGenerateWord(srcPath, destPath, map);
    }

    /**
     * 项目任务单.docx
     *
     * @param id
     */
    public void getXmrwdById(String srcPath, String destPath, String id) {
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        replaceAndGenerateWord(srcPath, destPath, map);
        List<ZjSpecialty> zjSpecialtyList = new ArrayList<>();
            List<String> list = new ArrayList<>();
        if (ids.length > 1) {
            zjSpecialtyList = zjSpecialtyMapper.toEntity(zjSpecialtyService.findAllByZjPublishId(ids[1]));
        } else {
            zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(id);
        }
        if(zjSpecialtyList != null && zjSpecialtyList.size() > 0){
            for (int i = 0; i < zjSpecialtyList.size(); i++) {
                list.add(zjSpecialtyList.get(i).getEstablishmentPersonName());
            }
        }
        List<ZjMember> zjMemberList = zjMemberService.findAllByZjProjectId(id);
        if (zjMemberList != null && zjMemberList.size() > 0) {
            for (int j = 0; j < zjMemberList.size(); j++) {
                if ("ejshr".equals(zjMemberList.get(j).getType())) {
                    list.add(zjMemberList.get(j).getPersonName());
                }
                if("sjshr".equals(zjMemberList.get(j).getType())){
                    list.add(zjMemberList.get(j).getPersonName());
                }
            }
        }
        findXmrwdById(destPath, list);
    }


    /**
     * 质量检查问题记录表.docx
     *
     * @param id
     */
    public void getZljcwtjlbById(String srcPath, String destPath,String id) {
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        StringBuffer bzr = new StringBuffer();
        StringBuffer esr = new StringBuffer();
        StringBuffer bzresr = new StringBuffer();
        StringBuffer sanshenPerson = new StringBuffer();
        List<ZjSpecialty> zjSpecialtyList = new ArrayList<>();
        if (ids.length > 1) {
            zjSpecialtyList = zjSpecialtyMapper.toEntity(zjSpecialtyService.findAllByZjPublishId(ids[1]));
        } else {
            zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(id);
        }
        if (zjSpecialtyList != null && zjSpecialtyList.size() > 0) {
            for (int i = 0; i < zjSpecialtyList.size(); i++) {
                bzr.append(zjSpecialtyList.get(i).getEstablishmentPersonName());
                bzr.append(",");
            }
        }
        List<ZjMember> zMemberList = zjMemberService.findAllByZjProjectId(id);
        if (zMemberList != null && zMemberList.size() > 0) {
            for (int j = 0; j < zMemberList.size(); j ++) {
                if("ejshr".equals(zMemberList.get(j).getType())){
                    esr.append(zMemberList.get(j).getPersonName());
                    esr.append(",");
                }
                if("sjshr".equals(zMemberList.get(j).getType())){
                    sanshenPerson.append(zMemberList.get(j).getPersonName());
                    sanshenPerson.append(",");
                }
            }
        }
        if(bzr != null && bzr.length() > 0){
            String bzPersin = bzr.substring(0,bzr.length() - 1);
            bzresr.append(bzPersin).append(";");
        }
        if(esr != null && esr.length() > 0){
            String esPerson = esr.substring(0,esr.length() - 1);
            bzresr.append(esPerson).append(";");
        }
        if(bzresr != null && bzresr.length() > 0){
            String bzesPerson = bzresr.toString();
            map.put("${bzresr}", bzesPerson);
        } else {
            map.put("${bzresr}", " ");
        }
        if(sanshenPerson != null && sanshenPerson.length() > 0){
            String sjshr = sanshenPerson.substring(0,esr.length() - 1);
            map.put("${sjshr}", sjshr);
        } else {
            map.put("${sjshr}", " ");
        }
        replaceAndGenerateWord(srcPath, destPath, map);
    }

    /**
     * 咨询成果文件质量控制流程单.docx
     * @param id
     */
    public void getZxcgwjzlkzlcdById(String srcPath, String destPath,String id){
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        replaceAndGenerateWord(srcPath, destPath, map);
        List<ZjSpecialty> zjSpecialtyList = new ArrayList<>();
        if (ids.length > 1) {
            zjSpecialtyList = zjSpecialtyMapper.toEntity(zjSpecialtyService.findAllByZjPublishId(ids[1]));
        } else {
            zjSpecialtyList = zjSpecialtyService.findAllByZjProjectId(id);
        }
        if (zjSpecialtyList != null && zjSpecialtyList.size() > 0) {
            findZxcgwjzlkzdById(destPath,zjSpecialtyList);
        }
    }


    /**
     * 咨询成果文件签发审批单.docx
     * @param id
     */
    public void getZxcgwjqfspdById(String srcPath, String destPath,String id){
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        List<Map<String, String>> wfAuditIdeaList = new ArrayList<>();
        List<ZjPublish> zjPublishes = zjPublishService.findAllByZjProjectId(ids[0]);
        if (zjPublishes.size() > 0) {
            wfAuditIdeaList = thsWFClient.getAuditIdea(zjPublishes.get(0).getId()).getBody();
        }
        map.put("${engineeringName}","");
        map.put("${auditIdea}","");
        map.put("${auditDate}","日期 年 月 日");
        if (wfAuditIdeaList.size() > 0) {
            for (Map<String, String> maps : wfAuditIdeaList) {
                if ("签发".equals(maps.get("auditStep"))) {
                    map.put("${engineeringName}",maps.get("auditerName"));
                    map.put("${auditIdea}",maps.get("auditIdea"));
                    if(!StringUtils.isEmpty(maps.get("auditDate"))){
                        String chinaDate = "日期: " + maps.get("auditDate").substring(0,4) + "年" + Integer.valueOf(maps.get("auditDate").substring(5,7)) + "月" +Integer.valueOf(maps.get("auditDate").substring(8,10)) + "日";
                        map.put("${auditDate}",chinaDate);
                    }
                }
            }
        }
        replaceAndGenerateWord(srcPath, destPath, map);
    }

    /**
     * 咨询成果文件质量控制流程单.docx 个性化方法
     * @param destPath
     * @param list
     */
    public void findZxcgwjzlkzdById(String destPath,List<ZjSpecialty> list){
        try {
            FileInputStream fileInputStream = new FileInputStream(destPath);
            XWPFDocument document = new XWPFDocument(fileInputStream);
            List<XWPFTable> tables = document.getTables();
            XWPFTable table = tables.get(0);
            XWPFTableRow header = table.getRow(5);
            for (int i = 0; i < list.size(); i++) {
                table.addRow(header, 5);
            }
            OutputStream output = new FileOutputStream(destPath);
            document.write(output);
            output.close();
            FileInputStream fileInputStream1 = new FileInputStream(destPath);
            XWPFDocument document1 = new XWPFDocument(fileInputStream1);
            List<XWPFTable> tables1 = document1.getTables();
            XWPFTable table1 = tables1.get(0);
            int num = 0;
            for (int i = 5; i < 5 + list.size(); i++) {
                num++;
                XWPFTableRow row = table1.getRow(i);
                row.getCell(1).setText(num+"."+list.get(i - 5).getEstablishmentPersonName());
                row.getCell(2).setText(list.get(i-5).getSpecialtyType());
            }
            OutputStream output1 = new FileOutputStream(destPath);
            document1.write(output1);
            output1.close();

            FileInputStream fileInputStream2 = new FileInputStream(destPath);
            XWPFDocument document2 = new XWPFDocument(fileInputStream2);
            List<XWPFTable> tables2 = document2.getTables();
            XWPFTable table2 = tables2.get(0);
            List<Map<String, String>> allList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                List<Map<String, String>> zjSpecialtyList = thsWFClient.getAuditIdea(list.get(i).getId()).getBody();
                if (zjSpecialtyList != null && zjSpecialtyList.size() > 0) {
                    for (Map<String, String> map : zjSpecialtyList) {
                        if ("自校".equals(map.get("auditStep"))) {
                            map.put("engineeringName",list.get(i).getEngineeringName());
                            map.put("ideaName","自校意见");
                            map.put("perType","编制人(签名):");
                            allList.add(map);
                        }
                    }
                    for (Map<String, String> map : zjSpecialtyList) {
                        if ("二级审核".equals(map.get("auditStep"))) {
                            map.put("engineeringName",list.get(i).getEngineeringName());
                            map.put("ideaName","复核意见");
                            map.put("perType","复核人(签名):");
                            allList.add(map);
                        }
                    }
                    for (Map<String, String> map : zjSpecialtyList) {
                        if ("三级审核".equals(map.get("auditStep"))) {
                            map.put("engineeringName",list.get(i).getEngineeringName());
                            map.put("ideaName","审定意见");
                            map.put("perType","审定人(签名):");
                            allList.add(map);
                        }
                    }
                }
            }
            String rowName = "";
            if (allList != null && allList.size() > 0) {
                for (Map<String, String> map: allList) {
                    if(!rowName.equals(map.get("engineeringName"))){
                        rowName = map.get("engineeringName");
                        XWPFTableRow header8 = table2.getRow((8 + list.size()));
                        XWPFTableRow header9 = table2.getRow((9 + list.size()));
                        XWPFTableRow header10 = table2.getRow((10 + list.size()));
                        table2.addRow(header8, 14+list.size());
                        table2.addRow(header9, 15+list.size());
                        table2.addRow(header10, 16+list.size());
                    } else {
                        XWPFTableRow header8 = table2.getRow((11 + list.size()));
                        XWPFTableRow header9 = table2.getRow((12 + list.size()));
                        XWPFTableRow header10 = table2.getRow((13 + list.size()));
                        table2.addRow(header8, 17+list.size());
                        table2.addRow(header9, 18+list.size());
                        table2.addRow(header10, 19+list.size());
                    }
                }
                table2.removeRow(13 + list.size());
                table2.removeRow(12 + list.size());
                table2.removeRow(11 + list.size());
                table2.removeRow(10 + list.size());
                table2.removeRow(9 + list.size());
                table2.removeRow(8 + list.size());
            }
            OutputStream output2 = new FileOutputStream(destPath);
            document2.write(output2);
            output2.close();
            FileInputStream fileInputStream3 = new FileInputStream(destPath);
            XWPFDocument document3 = new XWPFDocument(fileInputStream3);
            List<XWPFTable> tables3 = document3.getTables();
            XWPFTable table3 = tables3.get(0);
            int i = 8 + list.size();
            String name = "";
            for (Map<String,String> map :allList) {
                XWPFTableRow row = table3.getRow(i);
                if(!name.equals(map.get("engineeringName"))){
                    name = map.get("engineeringName");
                    row.getCell(0).setText(map.get("engineeringName"));
                }
                row.getCell(1).setText(map.get("ideaName"));
                row.getCell(2).setText(map.get("auditIdea"));
                i++;
                row = table3.getRow(i);
                row.getCell(3).setText(map.get("perType"));
                row.getCell(4).setText(map.get("auditerName"));
                i++;
                row = table3.getRow(i);
                if(!StringUtils.isEmpty(map.get("auditDate"))){
                    row.getCell(3).setText("日期: " + map.get("auditDate").substring(0,4) + "年" + Integer.valueOf(map.get("auditDate").substring(5,7)) + "月" +Integer.valueOf(map.get("auditDate").substring(8,10)) + "日");
                }
                i++;
            }
            OutputStream output3 = new FileOutputStream(destPath);
            document3.write(output3);
            output3.close();
        } catch (Exception e) {
            log.error("咨询成果文件质量控制流程单.docx 个性化方法",e);
        }
    }

    /**
     * 仅替换通用占位符
     *
     * @param id
     */
    public void getCBGGById(String srcPath, String destPath, String id) {
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[1]);
        replaceAndGenerateWord(srcPath, destPath, map);
    }

    /**
     * 出版咨询成果文件质量控制流程单.docx
     * @param id
     */
    public void getCBZxcgwjzlkzlcdById(String srcPath, String destPath,String id){
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        replaceAndGenerateWord(srcPath, destPath, map);
        List<ZjSpecialtyDTO> zjSpecialtyList = zjSpecialtyService.findAllByZjPublishId(ids[1]);
        if (zjSpecialtyList != null && zjSpecialtyList.size() > 0) {
            findZxcgwjzlkzdById(destPath,zjSpecialtyMapper.toEntity(zjSpecialtyList));
        }
    }

    /**
     * 出版封面、签署页、封底.docx
     *
     * @param id
     */
    public void getCBFmqsyfdById(String srcPath, String destPath, String id) {
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        Optional<ZjPublishDTO> zjPublishDTO = zjPublishService.findOne(ids[1]);
        if(zjPublishDTO.get().getPublishTime() != null){
            String cbPublishDate = zjPublishDTO.get().getPublishTime().toString();
            String cbDate = cbPublishDate.substring(0, 7);
            map.put("${publishDate}", getUpperDate(cbDate));
        }else {
            map.put("${publishDate}", "年  月");
        }
        replaceAndGenerateWord(srcPath, destPath, map);
    }

    /**
     * 出版质量检查问题记录表.docx
     *
     * @param id
     */
    public void getCBZljcwtjlbById(String srcPath, String destPath,String id) {
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        StringBuffer bzr = new StringBuffer();
        StringBuffer esr = new StringBuffer();
        StringBuffer bzresr = new StringBuffer();
        StringBuffer sanshenPerson = new StringBuffer();
        List<ZjSpecialtyDTO> zjSpecialtyList = zjSpecialtyService.findAllByZjPublishId(ids[1]);
        if (zjSpecialtyList != null && zjSpecialtyList.size() > 0) {
            for (int i = 0; i < zjSpecialtyList.size(); i++) {
                bzr.append(zjSpecialtyList.get(i).getEstablishmentPersonName());
                bzr.append(",");
                List<ZjSpecialtyAuditer> zjSpecialtyAuditerList = zjSpecialtyAuditerService.findAllByZjSpecialtyId(zjSpecialtyList.get(i).getId());
                if (zjSpecialtyAuditerList != null && zjSpecialtyAuditerList.size() > 0) {
                    for (int j = 0; j < zjSpecialtyAuditerList.size(); j ++) {
                        if("sjshr".equals(zjSpecialtyAuditerList.get(j).getType())){
                            sanshenPerson.append(zjSpecialtyAuditerList.get(j).getPersonName());
                            sanshenPerson.append(",");
                        }
                    }
                }
            }
        }
        if(bzr != null && bzr.length() > 0){
            String bzPersin = bzr.substring(0,bzr.length() - 1);
            bzresr.append(bzPersin).append(";");
        }
        if(esr != null && esr.length() > 0){
            String esPerson = esr.substring(0,esr.length() - 1);
            bzresr.append(esPerson).append(";");
        }
        if(bzresr != null && bzresr.length() > 0){
            String bzesPerson = bzresr.toString();
            map.put("${bzresr}", bzesPerson);
        } else {
            map.put("${bzresr}", " ");
        }
        if(sanshenPerson != null && sanshenPerson.length() > 0){
            String sjshr = sanshenPerson.substring(0,esr.length() - 1);
            map.put("${sjshr}", sjshr);
        } else {
            map.put("${sjshr}", " ");
        }
        replaceAndGenerateWord(srcPath, destPath, map);
    }

    /**
     * 出版工程预算（标底）、投标报价编制汇总表.docx
     *
     * @param id
     */
    public void getCBGcysbdtbbjbzhzbById(String srcPath, String destPath, String id) {
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        replaceAndGenerateWord(srcPath, destPath, map);
        List<ZjSpecialtyDTO> zjSpecialtyList = zjSpecialtyService.findAllByZjPublishId(ids[1]);
        if (zjSpecialtyList != null && zjSpecialtyList.size() > 0) {
            findGcysbdtbbjbzhzbById(destPath, zjSpecialtyMapper.toEntity(zjSpecialtyList));
        }
    }

    /**
     * 出版工程概预结算编制审核定案汇总表.docx
     *
     * @param id
     */
    public void getCBGcgyjsbzshdaById(String srcPath, String destPath, String id) {
        String[] ids = id.split(",");
        Map<String, String> map = getZjProjectInfoById(ids[0]);
        String busiType = map.get("${busiType}");
        if(!StringUtils.isEmpty(busiType) && !" ".equals(busiType)){
            if (busiType.equals("编制类-概算")) {
                map.put("${title}","概算编制");
            } else if (busiType.equals("编制类-施工图预算")) {
                map.put("${title}","预算编制");
            } else if (busiType.equals("编制类-竣工结算")) {
                map.put("${title}","结算编制");
            } else if (busiType.equals("审核类-概算")) {
                map.put("${title}","概算审核");
            } else if (busiType.equals("审核类-施工图预算")) {
                map.put("${title}","预算审核");
            } else if (busiType.equals("审核类-竣工结算")) {
                map.put("${title}","结算审核");
            } else if (busiType.equals("全过程造价类")) {
                map.put("${title}","全过程造价类");
            } else if (busiType.equals("经济纠纷鉴定和仲裁咨询")) {
                map.put("${title}","经济纠纷鉴定和仲裁咨询");
            } else {
                map.put("${title}","");
            }
        }else {
            map.put("${title}","");
        }
        replaceAndGenerateWord(srcPath, destPath, map);
        List<ZjSpecialtyDTO> zjSpecialtyList = zjSpecialtyService.findAllByZjPublishId(ids[1]);
        if (zjSpecialtyList != null && zjSpecialtyList.size() > 0) {
            findGcgyjsbzshdaById(destPath, zjSpecialtyMapper.toEntity(zjSpecialtyList));
        }
    }
}
