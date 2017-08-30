package com.avp.cdai.web.repository;

import com.avp.cdai.web.entity.LineCumulativeFlow;
import com.avp.cdai.web.entity.LineTimeShareFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by guo on 2017/8/11.
 */
public interface LineCumulativeFlowRepository extends JpaRepository<LineCumulativeFlow,Integer>,JpaSpecificationExecutor<LineCumulativeFlow> {
    //自定义查询
    @Query("select new LineCumulativeFlow(ssf.flowTime,sum(ssf.passengerFlow)) from LineCumulativeFlow as ssf  where ssf.lineId=:stationId and ssf.section=:section group by ssf.flowTime order by ssf.flowTime asc" )
    List<LineCumulativeFlow> getData(@Param("stationId") Integer stationid, @Param("section") Integer section);

    @Query("select new LineCumulativeFlow(ssf.flowTime,sum(ssf.passengerFlow)) from LineCumulativeFlow as ssf  where ssf.lineId=:stationId and ssf.section=:section and ssf.flowTime>=:startTime and ssf.flowTime<=:endTime group by ssf.flowTime order by ssf.flowTime asc" )
    List<LineCumulativeFlow> getData(@Param("stationId") Integer stationid, @Param("section") Integer section, @Param("startTime")Date startTime, @Param("endTime") Date endTime);
}
