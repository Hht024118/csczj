package com.thsware.framework.repository;

import com.thsware.framework.domain.ZjSpecialtyAuditer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ZjSpecialtyAuditer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZjSpecialtyAuditerRepository extends JpaRepository<ZjSpecialtyAuditer, String>, JpaSpecificationExecutor<ZjSpecialtyAuditer> {

    @Modifying()
    @Query( value = "UPDATE ZjSpecialtyAuditer SET personId = '',personName = '' WHERE zjSpecialtyId=?1 and type=?2")
    void cleanShrByType(String zjSpecialtyId, String type);

    @Modifying()
    @Query( value = "DELETE FROM ZjSpecialtyAuditer WHERE zjSpecialtyId=?1")
    void deleteBySpecialtyId(String zjSpecialtyId);

    List<ZjSpecialtyAuditer> findAllByZjSpecialtyId(String type);

    List<ZjSpecialtyAuditer> findAllByZjSpecialtyIdAndPersonIdAndType(String zjSpecialtyId,String personId, String type);

    List<ZjSpecialtyAuditer> findAllByZjProjectIdAndType(String zjProjectId, String type);

    List<ZjSpecialtyAuditer> findAllByZjSpecialtyIdAndType(String zjSpecialtyId, String type);

    List<ZjSpecialtyAuditer> findAllByZjProjectIdAndPersonIdAndType(String zjProjectId, String peronsId, String type);

    List<ZjSpecialtyAuditer> findAllByZjProjectId(String zjProjectId);
}
