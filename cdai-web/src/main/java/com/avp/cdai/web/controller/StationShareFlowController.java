package com.avp.cdai.web.controller;

import com.avp.cdai.web.entity.ObjStation;
import com.avp.cdai.web.entity.StationShareFlow;
import com.avp.cdai.web.entity.StationShareFlow_;
import com.avp.cdai.web.entity.ViewData;
import com.avp.cdai.web.repository.ObjStationRepository;
import com.avp.cdai.web.repository.StationShareFlowRepository;
import com.avp.cdai.web.rest.ResponseBuilder;
import com.avp.cdai.web.rest.ResponseCode;
import com.avp.cdai.web.rest.RestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by guo on 2017/8/11.
 */
@RestController
public class StationShareFlowController {
    @Autowired
    StationShareFlowRepository stationShareFlowRepository;
    @Autowired
    ObjStationRepository objStationRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "stationShareFlow",method = RequestMethod.GET)
    ResponseEntity<RestBody<StationShareFlow>> findAll(){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<StationShareFlow> list = stationShareFlowRepository.findAll();
            logger.debug("车站客流分时记录查询成功，数量为{}",list.size());
            builder.setResultEntity(list,ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }

        return builder.getResponseEntity();
    }

    @RequestMapping(value = "stationShareByConditions",method = RequestMethod.GET)
    ResponseEntity<RestBody<StationShareFlow>> findByConditions(@RequestParam("ids") List<Integer> ids, @RequestParam(value = "direct",required = false) Integer direct, Integer section, Date time){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<StationShareFlow> list = stationShareFlowRepository.findAll(where(byConditions(ids, direct,section, time)));
            if (list.size() <= 0) {
                logger.debug("车站客流分时多条件查询失败，结果为空");
            } else {
                logger.debug("车站客流分时多条件查询成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "viewDataStationShareByConditions",method = RequestMethod.GET)
    ResponseEntity<RestBody<ViewData<ObjStation>>> viewDataFindByConditions(@RequestParam("ids") List<Integer> ids, @RequestParam(value = "direct",required = false) Integer direct, Integer section, Date time){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Date startTime = null;
            Date endTime = null;
            if (time != null) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                String strStart = format1.format(time);
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
                String strEnd = format2.format(time);
                logger.debug("viewDataStationShareByConditions--起始时间为：{}，结束时间为：{}", strStart, strEnd);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    startTime = format.parse(strStart);
                    endTime = format.parse(strEnd);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            logger.debug("ids数据量为:({})", ids.size());
            ViewData<ObjStation> viewData = new ViewData<ObjStation>();
            Map<Integer,List<Integer>> flowMap = new HashMap<Integer,List<Integer>>();
            Map<Integer,ObjStation> objMap = new HashMap<Integer,ObjStation>();
            List<Date> dateList = null;
            //取范围较大的时间轴列表
            Integer tempCount = 0;
            for(int i = 0;i<ids.size();i++) {
                List<Integer> temp = new ArrayList<Integer>();
                List<StationShareFlow> list = stationShareFlowRepository.getData(ids.get(i), section,startTime,endTime);
                logger.debug("数据量为:({},{})", list.size(),ids.get(i));
                Integer key = null;
                List<Date> tempDateList = new ArrayList<Date>();
                for (StationShareFlow s:list){
                    temp.add(s.getFlowCount());
                    tempDateList.add(s.getFlowTime());
                }
                if(list.size() > tempCount) {
                    tempCount = list.size();
                    dateList = tempDateList;
                }
                flowMap.put(ids.get(i),temp);
            }
            logger.debug("flowMap数据量为:({})", flowMap.size());
//            List<ObjStation> objList = stationShareFlowRepository.getObjData();
            List<ObjStation> objList = objStationRepository.findBystationIdIn(ids);
            logger.debug("ObjStation数据量为:({})", objList.size());
            for (ObjStation obj:objList){
                Integer s_id = obj.getStationId();
                if(s_id == null){
                    logger.debug("s_id为null!");
                }
                objMap.put(s_id,obj);
            }
            viewData.setFlowCountMap(flowMap);
            viewData.setObjMap(objMap);
            viewData.setDateList(dateList);

            builder.setResultEntity(viewData,ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public Specification<StationShareFlow> byConditions(List<Integer> ids, Integer direct, Integer section, Date time) {
        return new Specification<StationShareFlow>() {
            public Predicate toPredicate(Root<StationShareFlow> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                logger.debug("stationShareByConditions请求的参数ids值为:{}", ids);
                if (ids != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(LineTimeShareFlow_.id), ids));
//                    predicate.getExpressions().add(builder.in(root.get(LineTimeShareFlow_.lineId)).in(ids));
                    predicate.getExpressions().add(root.get(StationShareFlow_.stationId).in(ids));
                }
//
                logger.debug("stationShareByConditions请求的参数direct值为:{}", direct);
                if (direct != null) {
                    predicate.getExpressions().add(builder.equal(root.get(StationShareFlow_.direction), direct));
                }

                logger.debug("stationShareByConditions请求的参数section值为:{}", section);
                if (section != null) {
                    predicate.getExpressions().add(builder.equal(root.get(StationShareFlow_.section), section));
                }
//
                logger.debug("stationShareByConditions请求的参数time值为:{}", time);
                if (time != null) {
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                    String strStart = format1.format(time);
                    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
                    String strEnd = format2.format(time);
                    logger.debug("起始时间为：{}，结束时间为：{}",strStart,strEnd);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startTime = null;
                    Date endTime = null;
                    try {
                        startTime = format.parse(strStart);
                        endTime = format.parse(strEnd);
                    }catch (Exception e){
                        logger.error(e.getMessage());
                    }
//                    predicate.getExpressions().add(builder.equal(root.get(StationShareFlow_.flowTime), time));
                    predicate.getExpressions().add(builder.between(root.get(StationShareFlow_.flowTime),startTime,endTime));
                }

                return predicate;
            }
        };
    }
    // Dynamic End
}
