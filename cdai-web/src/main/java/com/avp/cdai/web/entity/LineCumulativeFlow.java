package com.avp.cdai.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by pw on 2017/8/10.
 */
@Entity
@Table(name = "line_cumulative_passenger_flow",schema = "afccd")
public class LineCumulativeFlow {
    @Id
    private Integer id;
    @Column(name = "line_id")
    private Integer lineId;
    @Column(name = "direction")
    private Integer direction;
    @Column(name = "passenger_flow")
    private Integer passengerFlow;
    @Column(name = "section")
    private Integer section;
    @Column(name = "flow_timestamp")
    private Date flowTime;
    @Column(name = "insert_time")
    private Date insertTime;
    @Column(name = "line_name")
    private String lineName;

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }
}
