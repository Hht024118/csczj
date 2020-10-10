package com.thsware.framework.repository;

import com.thsware.framework.domain.ZjMember;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ZjMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZjMemberRepository extends JpaRepository<ZjMember, String>, JpaSpecificationExecutor<ZjMember> {
    @Modifying()
    @Query( value = "DELETE  FROM ZjMember WHERE zjProjectId=?1 ")
    void deleteMemberByZjProjectId(String zjProjectId);

    List<ZjMember> findAllByZjProjectId(String zjProjectId);

}
