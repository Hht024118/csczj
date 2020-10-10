package com.thsware.framework.repository;

import com.thsware.framework.domain.ZjProjectArchive;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ZjProjectArchive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZjProjectArchiveRepository extends JpaRepository<ZjProjectArchive, String>, JpaSpecificationExecutor<ZjProjectArchive> {

}
