package com.avp.cdai.web.repository;

import com.avp.cdai.web.entity.StationCumulativeFlow;
import com.avp.cdai.web.entity.StationShareFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by guo on 2017/8/11.
 */
public interface StationCumulativeFlowRepository extends JpaRepository<StationCumulativeFlow,Integer>,JpaSpecificationExecutor<StationCumulativeFlow> {
    //自定义查询
    @Query("select new StationCumulativeFlow(ssf.flowTime,sum(ssf.flowCount)) from StationCumulativeFlow as ssf  where ssf.stationId=:stationId and ssf.section=:section group by ssf.flowTime order by ssf.flowTime asc" )
    List<StationCumulativeFlow> getData(@Param("stationId") Integer stationid, @Param("section") Integer section);

    @Query("select new StationCumulativeFlow(ssf.flowTime,sum(ssf.flowCount)) from StationCumulativeFlow as ssf  where ssf.stationId=:stationId and ssf.section=:section and ssf.flowTime>=:startTime and ssf.flowTime<=:endTime group by ssf.flowTime order by ssf.flowTime asc" )
    List<StationCumulativeFlow> getData(@Param("stationId") Integer stationid, @Param("section") Integer section, @Param("startTime")Date startTime, @Param("endTime") Date endTime);
}
