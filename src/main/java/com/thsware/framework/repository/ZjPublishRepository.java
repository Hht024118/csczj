package com.thsware.framework.repository;

import com.thsware.framework.domain.ZjPublish;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ZjPublish entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZjPublishRepository extends JpaRepository<ZjPublish, String>, JpaSpecificationExecutor<ZjPublish> {

    List<ZjPublish> findAllByZjProjectId(String zjProjectId);
}
