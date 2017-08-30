package com.avp.cdai.web.repository;

import com.avp.cdai.web.entity.LineTimeShareFlow;
import com.avp.cdai.web.entity.StationShareFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by guo on 2017/8/8.
 */
public interface LineTimeShareFlowRepository extends JpaRepository<LineTimeShareFlow,Integer> , JpaSpecificationExecutor<LineTimeShareFlow> {
    //自定义查询
    @Query("select new LineTimeShareFlow(ssf.timestamp,sum(ssf.passengerFlow)) from LineTimeShareFlow as ssf  where ssf.lineId=:stationId and ssf.section=:section group by ssf.timestamp order by ssf.timestamp asc" )
    List<LineTimeShareFlow> getData(@Param("stationId") Integer stationid, @Param("section") Integer section);

    @Query("select new LineTimeShareFlow(ssf.timestamp,sum(ssf.passengerFlow)) from LineTimeShareFlow as ssf  where ssf.lineId=:stationId and ssf.section=:section and ssf.timestamp>=:startTime and ssf.timestamp<=:endTime group by ssf.timestamp order by ssf.timestamp asc" )
    List<LineTimeShareFlow> getData(@Param("stationId") Integer stationid, @Param("section") Integer section, @Param("startTime")Date startTime, @Param("endTime") Date endTime);
}
