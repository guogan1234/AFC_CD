package com.avp.cdai.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by guo on 2017/8/8.
 */
@Entity
@Table(name = "line_time_sharing_passenger_flow",schema = "afccd")
public class LineTimeShareFlow{
    @Id
    private Integer id;
    @Column(name = "line_id")
    private Integer lineId;
    @Column(name = "direction")
    private Integer direction;
    @Column(name = "passenger_flow")
    private Integer passengerFlow;
    @Column(name = "flow_timestamp")
    private Date timestamp;
    @Column(name = "insert_time")
    private Date insertTime;
    @Column(name = "section")
    private Integer section;

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
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

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getPassengerFlow() {
        return passengerFlow;
    }

    public void setPassengerFlow(Integer passengerFlow) {
        this.passengerFlow = passengerFlow;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public LineTimeShareFlow(){}

    public LineTimeShareFlow(Date flowTime,Long flowCount){
        this.timestamp = flowTime;
        this.passengerFlow = flowCount.intValue();
    }
}
