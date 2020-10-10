package com.thsware.framework.repository;

import com.thsware.framework.domain.ZjProjectChangelog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ZjProjectChangelog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZjProjectChangelogRepository extends JpaRepository<ZjProjectChangelog, String>, JpaSpecificationExecutor<ZjProjectChangelog> {

}
