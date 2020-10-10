package com.thsware.framework.repository;

import com.thsware.framework.domain.ZjSpecialtyIdea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ZjSpecialtyIdea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZjSpecialtyIdeaRepository extends JpaRepository<ZjSpecialtyIdea, String>, JpaSpecificationExecutor<ZjSpecialtyIdea> {

    @Modifying()
    @Query( value = "UPDATE ZjSpecialtyIdea SET isHistory = '1' WHERE zjSpecialtyId=?1")
    void updateIsHistory(String zjSpecialtyId);

    @Modifying()
    @Query( value = "UPDATE ZjSpecialtyIdea SET isHistory = '1' WHERE zjProjectId=?1")
    void updateIsHistoryByProjectId(String zjProjectId);

    List<ZjSpecialtyIdea> findAllByZjSpecialtyIdAndAuditTypeAndIsHistory(String zjSpecialtyId, String auditType, String isHistory);

    List<ZjSpecialtyIdea> findAllByZjProjectIdAndAuditTypeAndIsHistory(String zjProjectId, String auditType, String isHistory);

    List<ZjSpecialtyIdea> findAllByZjProjectIdAndAuditTypeAndIsHistoryAndAuditerId(String zjProjectId, String auditType, String isHistory, String auditerId);

    List<ZjSpecialtyIdea> findAllByZjProjectId(String zjProjectId);

    List<ZjSpecialtyIdea> findAllByZjProjectIdAndAuditerIdAndIsHistoryAndValidEjsh(String zjProjectId, String auditerId, String isHistroy, String validEjsh);

}
