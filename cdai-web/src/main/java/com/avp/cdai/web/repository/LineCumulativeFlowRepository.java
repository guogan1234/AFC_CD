package com.avp.cdai.web.repository;

import com.avp.cdai.web.entity.LineCumulativeFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by pw on 2017/8/11.
 */
public interface LineCumulativeFlowRepository extends JpaRepository<LineCumulativeFlow,Integer>,JpaSpecificationExecutor<LineCumulativeFlow> {
}
