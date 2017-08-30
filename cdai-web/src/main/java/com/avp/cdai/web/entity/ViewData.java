package com.avp.cdai.web.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by guo on 2017/8/21.
 */
public class ViewData<T> {
    private Map<Integer,List<Integer>> flowCountMap;
    private Map<Integer,T> objMap;
    private List<Date> dateList;

    public Map<Integer, List<Integer>> getFlowCountMap() {
        return flowCountMap;
    }

    public void setFlowCountMap(Map<Integer, List<Integer>> flowCountMap) {
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
