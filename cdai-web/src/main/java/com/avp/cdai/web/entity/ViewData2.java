package com.avp.cdai.web.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by guo on 2017/8/24.
 */
public class ViewData2<T> {
    private Map<Integer,List<Double>> flowCountMap;
    private Map<Integer,T> objMap;
    private List<Date> dateList;

    public Map<Integer, List<Double>> getFlowCountMap() {
        return flowCountMap;
    }

    public void setFlowCountMap(Map<Integer, List<Double>> flowCountMap) {
        this.flowCountMap = flowCountMap;
    }

    public Map<Integer, T> getObjMap() {
        return objMap;
    }

    public void setObjMap(Map<Integer, T> objMap) {
        this.objMap = objMap;
    }

    public List<Date> getDateList() {
        return dateList;
    }

    public void setDateList(List<Date> dateList) {
        this.dateList = dateList;
    }
}
