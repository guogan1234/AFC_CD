package com.avp.cdai.web.repository;

import com.avp.cdai.web.entity.LineTimeShareFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by pw on 2017/8/8.
 */
public interface LineTimeShareFlowRepository extends JpaRepository<LineTimeShareFlow,Integer> , JpaSpecificationExecutor<LineTimeShareFlow> {
}
