package com.avp.cdai.web.repository;

import com.avp.cdai.web.entity.TestEntityBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by guo on 2017/8/11.
 */
public interface TestEntityBaseRepository extends JpaRepository<TestEntityBase,Integer>,JpaSpecificationExecutor<TestEntityBase> {
}
