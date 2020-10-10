package com.thsware.framework.repository;

import com.thsware.framework.domain.ZjActors;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ZjActors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZjActorsRepository extends JpaRepository<ZjActors, String>, JpaSpecificationExecutor<ZjActors> {

    List<ZjActors> findAllByProjectId(String projectId);

    List<ZjActors> findAllByProjectIdAndStepAndPersonId(String projectId, String step, String personId);
}
