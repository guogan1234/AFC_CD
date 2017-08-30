package com.avp.cdai.web.controller;

import com.avp.cdai.web.entity.*;
import com.avp.cdai.web.repository.TicketShareFlowRepository;
import com.avp.cdai.web.repository.TicketTypeRepository;
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
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by guo on 2017/8/16.
 */
@RestController
public class TicketShareFlowController {
    @Autowired
    TicketShareFlowRepository ticketShareFlowRepository;
    @Autowired
    TicketTypeRepository ticketTypeRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "ticketShareFlow",method = RequestMethod.GET)
    ResponseEntity<RestBody<TicketShareFlow>> findAll(){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<TicketShareFlow> list = ticketShareFlowRepository.findAll();
            logger.debug("票卡客流分时记录查询数量为：{}",list.size());
            builder.setResultEntity(list,ResponseCode.RETRIEVE_SUCCEED);
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "viewDataTicketShareByConditions",method = RequestMethod.GET)
    ResponseEntity<RestBody<ViewData<TicketType>>> findByConditions(@RequestParam("time")Date time,@RequestParam("section")Integer section){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
//        List<TicketShareFlow> list = ticketShareFlowRepository.getData2(section,id);
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
        //定义数据
        ViewData<TicketType> viewData = new ViewData<TicketType>();
        Map<Integer,List<Integer>> flowMap = new HashMap<Integer,List<Integer>>();
        Map<Integer,TicketType> objMap = new HashMap<Integer,TicketType>();
        List<Date> dateList = null;
        //获取票卡列表
        List<TicketType> ticketList = ticketTypeRepository.findAll();
        logger.debug("ticketList--票卡数量为:{}",ticketList.size());
        Integer max = 0;
        for (TicketType t:ticketList) {
            Integer id = t.getTicketId();
            objMap.put(id, t);

            List<Integer> tempFlow = new ArrayList<Integer>();
            //返回Object[]查询结果的解析方式
            //返回List<Object[]>查询结果的解析方式
//            logger.debug("查询参数--id:{},section:{},start:{},end:{}", id, section, startTime, endTime);
            List<Object[]> list = ticketShareFlowRepository.getData4(id, section, startTime, endTime);
//            logger.debug("TicketShare--List数量为:{}", list.size());
            Integer size = list.size();

            List<Date> tempDate = new ArrayList<Date>();
            for (int j = 0; j < size; j++) {
                Object[] objList = list.get(j);
                for (int i = 0; i < objList.length; i++) {
                    if (size > max){
                        if (i == 0) {
                            Date d = (Date) objList[i];
                            tempDate.add(d);
                        }
                    }
                    if (i == 1) {
                        BigInteger count = (BigInteger) objList[i];
                        tempFlow.add(count.intValue());
                    }
                }
            }
            flowMap.put(id, tempFlow);
            if (size > max){
                max = size;
                dateList = tempDate;
            }
        }
        viewData.setFlowCountMap(flowMap);
        viewData.setObjMap(objMap);
        viewData.setDateList(dateList);
        builder.setResultEntity(viewData,ResponseCode.RETRIEVE_SUCCEED);
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "viewDataTicketShareByConditions2",method = RequestMethod.GET)
    ResponseEntity<RestBody<ViewData<TicketType>>> findByConditions2(@RequestParam("time")Date time,@RequestParam("section")Integer section){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        //获取起止时间
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
        //定义数据
        ViewData<TicketType> viewData = new ViewData<TicketType>();
        Map<Integer,List<Integer>> flowMap = new HashMap<Integer,List<Integer>>();
        Map<Integer,TicketType> objMap = new HashMap<Integer,TicketType>();
        List<Date> dateList = null;
        //获取票卡列表
        List<TicketType> ticketList = ticketTypeRepository.findAll();
        logger.debug("ticketList--票卡数量为:{}",ticketList.size());
        List<Object[]> list = ticketShareFlowRepository.getAllData(section,startTime,endTime);
        Integer max = 0;
        for(TicketType t:ticketList){
            Integer key = t.getTicketId();
            List<Integer> tempList = new ArrayList<Integer>();
            List<Date> tempDate = new ArrayList<Date>();
            objMap.put(key,t);
            for(int i = 0;i<list.size();i++){
                Object[] obj = list.get(i);
                if(obj.length!=3){
                    //数据长度不对
                }
                Integer dataKey = (Integer) obj[2];
                if(dataKey == key){
                    BigInteger count = (BigInteger) obj[1];
                    tempList.add(count.intValue());
                    Date date = (Date)obj[0];
                    tempDate.add(date);
                }
            }
            if(tempDate.size()>max){
                max = tempDate.size();
                dateList = tempDate;
            }
            flowMap.put(key,tempList);
        }
        //填充模型
        viewData.setFlowCountMap(flowMap);
        viewData.setObjMap(objMap);
        viewData.setDateList(dateList);

        builder.setResultEntity(viewData,ResponseCode.RETRIEVE_SUCCEED);
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "viewDataWithZeroTicketShare",method = RequestMethod.GET)
    ResponseEntity<RestBody<ViewDataWithZero<Integer,TicketType>>> findData(@RequestParam("time")Date time){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        //获取起止时间
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
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "ticketShareByConditions",method = RequestMethod.GET)
    ResponseEntity<RestBody<TicketShareFlow>> findByConditions(@RequestParam("ids") List<Integer> ids,@RequestParam(value = "lineIds",required = false) List<Integer> lineIds,@RequestParam(value = "stationIds",required = false) List<Integer> stationIds, @RequestParam(value = "direct",required = false) Integer direct, Integer section, Date time){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<TicketShareFlow> list = ticketShareFlowRepository.findAll(where(byConditions(ids,lineIds,stationIds, direct,section, time)));
            if (list.size() <= 0) {
                logger.debug("票卡客流分时多条件查询失败，结果为空");
            } else {
                logger.debug("票卡客流分时多条件查询成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public Specification<TicketShareFlow> byConditions(List<Integer> ids,List<Integer> lineIds, List<Integer> stationIds,Integer direct, Integer section, Date time) {
        return new Specification<TicketShareFlow>() {
            public Predicate toPredicate(Root<TicketShareFlow> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                logger.debug("ticketShareByConditions请求的参数ids值为:{}", ids);
                if (ids != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(LineTimeShareFlow_.id), ids));
//                    predicate.getExpressions().add(builder.in(root.get(LineTimeShareFlow_.lineId)).in(ids));
                    predicate.getExpressions().add(root.<Integer>get(TicketShareFlow_.ticketId).in(ids));
                }
                logger.debug("ticketShareByConditions请求的参数lineIds值为:{}", lineIds);
                if (lineIds != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(LineTimeShareFlow_.id), ids));
//                    predicate.getExpressions().add(builder.in(root.get(LineTimeShareFlow_.lineId)).in(ids));
                    predicate.getExpressions().add(root.<Integer>get(TicketShareFlow_.lineId).in(lineIds));
                }
                logger.debug("ticketShareByConditions请求的参数stationIds值为:{}", stationIds);
                if (stationIds != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(LineTimeShareFlow_.id), ids));
//                    predicate.getExpressions().add(builder.in(root.get(LineTimeShareFlow_.lineId)).in(ids));
                    predicate.getExpressions().add(root.<Integer>get(TicketShareFlow_.stationId).in(stationIds));
                }
//
                logger.debug("ticketShareByConditions请求的参数direct值为:{}", direct);
                if (direct != null) {
                    predicate.getExpressions().add(builder.equal(root.get(TicketShareFlow_.direction), direct));
                }

                logger.debug("ticketShareByConditions请求的参数section值为:{}", section);
                if (section != null) {
                    predicate.getExpressions().add(builder.equal(root.get(TicketShareFlow_.section), section));
                }
//
                logger.debug("ticketShareByConditions请求的参数time值为:{}", time);
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
//                    predicate.getExpressions().add(builder.equal(root.get(LineCumulativeFlow_.flowTime), time));
                    predicate.getExpressions().add(builder.between(root.get(TicketShareFlow_.timestamp),startTime,endTime));
                }

                return predicate;
            }
        };
    }
    // Dynamic End
}
