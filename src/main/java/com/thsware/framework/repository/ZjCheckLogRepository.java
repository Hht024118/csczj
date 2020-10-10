package com.thsware.framework.repository;

import com.thsware.framework.domain.ZjCheckLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ZjCheckLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZjCheckLogRepository extends JpaRepository<ZjCheckLog, String>, JpaSpecificationExecutor<ZjCheckLog> {

    @Modifying()
    @Query( value = "Delete from ZjCheckLog WHERE zjSpecialtyId=?1 and chekcStep in (?2,?3)")
    void cleanCheckLogByZjSpecialtyId(String zjSpecialtyId, String chekcStepOne, String chekcStepTwo );

    @Modifying()
    @Query( value = "Delete from ZjCheckLog WHERE zjSpecialtyId=?1")
    void cleanCheckLog(String zjSpecialtyId);

    @Modifying()
    @Query( value = "Delete from ZjCheckLog WHERE zjSpecialtyId=?1 and chekcStep in ('ejsh','sjsh')")
    void cleanCheckLogES(String zjSpecialtyId);

    @Modifying()
    @Query( value = "Delete from ZjCheckLog WHERE zjSpecialtyId=?1 and chekcStep = 'sjsh'")
    void cleanCheckLogS(String zjSpecialtyId);

}
