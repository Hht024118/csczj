package com.thsware.framework.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "zh-cn";

    /**
     * 业务类型
     */
    public static final String ZJ_BUSITYPE = "造价咨询";

    /**
     * 造价流程
     */
    public static final String ZJ_PROJECT_XMDJ = "项目登记";
    public static final String ZJ_PROJECT_XMCH = "项目策划";
    public static final String ZJ_PROJECT_CHSH = "策划审核";
    public static final String ZJ_PROJECT_ZJ = "自校";
    public static final String ZJ_PROJECT_ZJHZ = "自校汇总";
    public static final String ZJ_PROJECT_EJSH = "二级审核";
    public static final String ZJ_PROJECT_SJSH = "三级审核";
    public static final String ZJ_PROJECT_XMHZ = "项目汇总";
    public static final String ZJ_PROJECT_QF = "签发";
    public static final String ZJ_PROJECT_CB = "出版";
    public static final String ZJ_PROJECT_JS = "结束";

    /**
     * 造价项目流程状态
     */
    public static final String ZJ_FLOW_STATE_XMDJ = "xmdj";
    public static final String ZJ_FLOW_STATE_XMCH = "xmch";
    public static final String ZJ_FLOW_STATE_CHSH = "chsh";
    public static final String ZJ_FLOW_STATE_SSZ = "ssz";
    public static final String ZJ_FLOW_STATE_DGD = "dgd";
    public static final String ZJ_FLOW_STATE_YGD = "ygd";

    /**
     * 造价工程流程状态
     */
    public static final String ZJ_SPECIALTY_FLOW_STATE_ZJ = "zj";
    public static final String ZJ_SPECIALTY_FLOW_STATE_ZJHZ = "zjhz";
    public static final String ZJ_SPECIALTY_FLOW_STATE_EJSH = "ejsh";
    public static final String ZJ_SPECIALTY_FLOW_STATE_SJSH = "sjsh";
    public static final String ZJ_SPECIALTY_FLOW_STATE_XMHZ = "xmhz";
    public static final String ZJ_SPECIALTY_FLOW_STATE_QF = "qf";
    public static final String ZJ_SPECIALTY_FLOW_STATE_CB = "cb";
    public static final String ZJ_SPECIALTY_FLOW_STATE_CBWC = "cbwc";
    public static final String ZJ_SPECIALTY_FLOW_STATE_CBJS = "cbjs";
    public static final String ZJ_FLOW_STATE_JS = "js";
    public static final String ZJ_FLOW_STATE_BZRXG = "bzrxg";

    /**
     * 签发出版流程出版完成选择步骤
     */
    public static final String ZJ_SPECIALTY_STEP_NAME_GCSPJS = "工程审批结束";
    public static final String ZJ_SPECIALTY_STEP_NAME_XMHZ = "项目汇总";


    /**
     * 造价项目状态
     */
    public static final String ZJ_PROJECT_STATE_ZC = "暂存";
    public static final String ZJ_PROJECT_STATE_YTJ = "已提交";
    public static final String ZJ_PROJECT_STATE_SPZ = "审批中";
    public static final String ZJ_PROJECT_STATE_DGD = "待归档";
    public static final String ZJ_PROJECT_STATE_YGD = "已归档";
    public static final String ZJ_PROJECT_STATE_YGB = "已关闭";


    /**
     * 工程工作安排驳回审核状态
     */
    public static final String ZJ_SPECIALTY_AUDIT_STATE_DES = "des";// 待二审
    public static final String ZJ_SPECIALTY_AUDIT_STATE_DSS = "dss";// 待三审
    public static final String ZJ_SPECIALTY_AUDIT_STATE_DBZRXG = "dbzrxg";// 待编制人修改
    public static final String ZJ_SPECIALTY_AUDIT_STATE_SPTG = "sptg";// 审批通过
    public static final String ZJ_SPECIALTY_AUDIT_STATE_WAIT_DBZRXG = "waitDbzrxg";// 工程二/三审未结束，但已经有人驳回，等所有人都提交意见后，修改工程状态为待编制人修改
    
    private Constants() {
    }
}
