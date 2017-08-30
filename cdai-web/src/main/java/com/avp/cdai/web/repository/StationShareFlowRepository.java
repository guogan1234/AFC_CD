package com.avp.cdai.web.repository;

import com.avp.cdai.web.entity.ObjStation;
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
public interface StationShareFlowRepository extends JpaRepository<StationShareFlow,Integer>,JpaSpecificationExecutor<StationShareFlow> {
    //@Query("select sum(station_id)/count(*) as a,sum(passenger_flow) as b,flow_timestamp from StationShareFlow ssf where  and section=30 and flow_timestamp>='2017-05-01 00:00:00' and  flow_timestamp<='2017-05-01 23:59:59' group by flow_timestamp")

    //自定义查询
    @Query("select new StationShareFlow(ssf.flowTime,sum(ssf.flowCount)) from StationShareFlow as ssf  where ssf.stationId=:stationId and ssf.section=:section group by ssf.flowTime order by ssf.flowTime asc" )
    List<StationShareFlow> getData(@Param("stationId") Integer stationid,@Param("section") Integer section);

    @Query("select new StationShareFlow(ssf.flowTime,sum(ssf.flowCount)) from StationShareFlow as ssf  where ssf.stationId=:stationId and ssf.section=:section and ssf.flowTime>=:startTime and ssf.flowTime<=:endTime group by ssf.flowTime order by ssf.flowTime asc" )
    List<StationShareFlow> getData(@Param("stationId") Integer stationid, @Param("section") Integer section, @Param("startTime")Date startTime,@Param("endTime") Date endTime);

//    @Query("select new ObjStaion(obj.id,obj.stationId,obj.stationName,obj.lineId,obj.syncTime) from ObjStation obj")
//    List<ObjStation> getObjData();//调用Error
}
