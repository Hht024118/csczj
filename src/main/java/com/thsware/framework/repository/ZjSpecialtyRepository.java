package com.thsware.framework.repository;

import com.thsware.framework.domain.ZjSpecialty;
import com.thsware.framework.service.dto.ZjSpecialtyDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ZjSpecialty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZjSpecialtyRepository extends JpaRepository<ZjSpecialty, String>, JpaSpecificationExecutor<ZjSpecialty> {

    @Modifying()
    @Query( value = "UPDATE ZjSpecialty SET establishmentPersonId = '',establishmentPersonName = '' WHERE zjProjectId=?1")
    void cleanBzr(String zjProjectId);

    @Modifying()
    @Query( value = "DELETE  FROM ZjSpecialty WHERE zjProjectId=?1 ")
    void deleteZjSpecialtyByZjProjectId(String zjProjectId);

    List<ZjSpecialty> findAllByZjProjectIdOrderByCreatedDateDesc(String zjProjectId);

    List<ZjSpecialty> findAllByZjPublishIdOrderByCreatedDateDesc(String zjPublishId);
}
