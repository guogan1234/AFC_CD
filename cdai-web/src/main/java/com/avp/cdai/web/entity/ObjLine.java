package com.avp.cdai.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by pw on 2017/8/4.
 */
@Entity
@Table(name = "obj_line",schema = "afccd")
public class ObjLine {
    @Id
    private Integer id;
    @Column(name = "line_id")
    private Integer lineId;
    @Column(name = "line_name")
    private String lineName;
    @Column(name = "sync_time")
    private Date syncTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

}
