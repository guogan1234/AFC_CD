package com.avp.cdai.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by guo on 2017/8/11.
 */
@Entity
@Table(name = "line_time_sharing_passenger_flow",schema = "afccd")
public class TestEntityBase extends EntityBase {
    @Id
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "passenger_flow")
    private int flowCount;

    public int getFlowCount() {
        return flowCount;
    }

    public void setFlowCount(int flowCount) {
        this.flowCount = flowCount;
    }
}
