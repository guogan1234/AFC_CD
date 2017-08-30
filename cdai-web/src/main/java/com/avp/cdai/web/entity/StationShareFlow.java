package com.avp.cdai.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by guo on 2017/8/11.
 */
@Entity
@Table(name = "station_time_sharing_passenger_flow",schema = "afccd")
public class StationShareFlow {
    @Id
    private Integer id;
    @Column(name = "line_id")
    private Integer lineId;
    @Column(name = "station_id")
    private Integer stationId;
    @Column(name = "direction")
    private Integer direction;
    @Column(name = "passenger_flow")
    private Integer flowCount;
    @Column(name = "section")
    private Integer section;
    @Column(name = "flow_timestamp")
    private Date flowTime;
    @Column(name = "insert_time")
    private Date insertTime;
    @Column(name = "station_name")
    private String stationName;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getFlowCount() {
        return flowCount;
    }

    public void setFlowCount(Integer flowCount) {
        this.flowCount = flowCount;
    }

    public Date getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(Date flowTime) {
        this.flowTime = flowTime;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public StationShareFlow(){}

    public StationShareFlow(Date flowTime,Long flowCount){
        this.flowTime = flowTime;
        this.flowCount = flowCount.intValue();
//        this.flowCount = flowCount;
    }
}
