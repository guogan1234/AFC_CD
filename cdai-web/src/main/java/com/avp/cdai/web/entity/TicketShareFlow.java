package com.avp.cdai.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by guo on 2017/8/16.
 */
@Entity
@Table(name = "ticket_share_passenger_flow",schema = "afccd")
public class TicketShareFlow {
    @Id
    private Integer id;
    @Column(name = "ticket_id")
    private Integer ticketId;
    @Column(name = "line_id")
    private Integer lineId;
    @Column(name = "station_id")
    private Integer stationId;
    @Column(name = "direction")
    private Integer direction;
    @Column(name = "section")
    private Integer section;
    @Column(name = "passenger_flow")
    private Integer flowCount;
    @Column(name = "flow_timestamp")
    private Date timestamp;
    @Column(name = "insert_time")
    private Date insertTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
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

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getFlowCount() {
        return flowCount;
    }

    public void setFlowCount(Integer flowCount) {
        this.flowCount = flowCount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public TicketShareFlow(){}

    public TicketShareFlow(Date flowTime,Long flowCount){
        this.timestamp = flowTime;
        this.flowCount = flowCount.intValue();
    }
}
