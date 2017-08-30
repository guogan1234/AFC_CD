package com.avp.cdai.web.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by guo on 2017/8/10.
 */
//@Entity(name = "entity_base")
public class EntityBase {
    @Id
    private Integer id;
    @Column(name = "flow_timestamp")
    private Date syncTime;
    @Column(name = "insert_time")
    private Date insertTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}
