package com.avp.cdai.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by pw on 2017/8/7.
 */
@Entity
@Table(name = "obj_station",schema = "public")
public class ObjStation {
    @Id
    private Integer id;
    @Column(name = "station_id")
    private Integer stationId;
    @Column(name = "station_name")
    private String stationName;
    @Column(name = "line_id")
    private Integer lineId;
    @Column(name = "sync_time")
    private Date syncTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }
}
