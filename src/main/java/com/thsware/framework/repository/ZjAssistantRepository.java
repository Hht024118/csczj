package com.thsware.framework.repository;

import com.thsware.framework.domain.ZjAssistant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ZjAssistant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZjAssistantRepository extends JpaRepository<ZjAssistant, String>, JpaSpecificationExecutor<ZjAssistant> {

    @Modifying()
    @Query( value = "DELETE  FROM ZjAssistant WHERE zjSpecialtyId=?1 ")
    void deleteByZjSpecialtyId(String zjSpecialtyId);
}
