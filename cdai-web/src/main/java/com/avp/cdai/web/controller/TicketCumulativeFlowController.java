package com.avp.cdai.web.controller;

import com.avp.cdai.web.entity.TicketCumulativeFlow;
import com.avp.cdai.web.entity.TicketCumulativeFlow_;
import com.avp.cdai.web.entity.TicketType;
import com.avp.cdai.web.entity.ViewData2;
import com.avp.cdai.web.repository.TicketCumulativeFlowRepository;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by guo on 2017/8/17.
 */
@RestController
public class TicketCumulativeFlowController {
    @Autowired
    TicketCumulativeFlowRepository ticketCumulativeFlowRepository;
    @Autowired
    TicketTypeRepository ticketTypeRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "ticketSumFlow",method = RequestMethod.GET)
    ResponseEntity<RestBody<TicketCumulativeFlow>> findAll(){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<TicketCumulativeFlow> list = ticketCumulativeFlowRepository.findAll();
            logger.debug("票卡客流累计记录查询成功，记录数量为{}",list.size());
            builder.setResultEntity(list,ResponseCode.RETRIEVE_SUCCEED);
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "viewDataTicketSumByConditions",method = RequestMethod.GET)
    ResponseEntity<RestBody<ViewData2<TicketType>>> findByConditions(@RequestParam("section") Integer section,@RequestParam("direct") Integer direct,@RequestParam("time") Date time){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            Date startTime = null;
            Date endTime = null;
            if (time != null) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                String strStart = format1.format(time);
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
                String strEnd = format2.format(time);
                logger.debug("viewDataTicketSumByConditions--起始时间为：{}，结束时间为：{}", strStart, strEnd);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startTime = format.parse(strStart);
                endTime = format.parse(strEnd);
            }
            ViewData2<TicketType> viewData = new ViewData2<TicketType>();
            Map<Integer,List<Double>> flowMap = new HashMap<Integer,List<Double>>();
            Map<Integer,TicketType> objMap = new HashMap<Integer,TicketType>();
            List<Date> dateList = null;

            List<TicketCumulativeFlow> list = ticketCumulativeFlowRepository.getData(direct,section,startTime,endTime);
            double sum = 0;
            for (TicketCumulativeFlow t:list){
                sum += t.getFlowCount();
            }
            for(TicketCumulativeFlow t:list){
                List<Double> countList = new ArrayList<Double>();
                double d = t.getFlowCount();
                double value = (d/sum)*100;
                //double格式化输出
                DecimalFormat formatter = new DecimalFormat("##.#");
                String strD = formatter.format(value);
                double dValue = Double.valueOf(strD).doubleValue();

                countList.add(dValue);
                flowMap.put(t.getTicketId(),countList);
            }
            List<TicketType> objList = ticketTypeRepository.findAll();
            for (TicketType t:objList){
                objMap.put(t.getTicketId(),t);
            }
            viewData.setFlowCountMap(flowMap);
            viewData.setObjMap(objMap);
            viewData.setDateList(dateList);

            builder.setResultEntity(viewData,ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "ticketSumByConditions",method = RequestMethod.GET)
    ResponseEntity<RestBody<TicketCumulativeFlow>> findByConditions(@RequestParam("ids") List<Integer> ids, @RequestParam(value = "lineIds",required = false) List<Integer> lineIds, @RequestParam(value = "stationIds",required = false) List<Integer> stationIds, @RequestParam(value = "direct",required = false) Integer direct, Integer section, Date time){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<TicketCumulativeFlow> list = ticketCumulativeFlowRepository.findAll(where(byConditions(ids,lineIds,stationIds, direct,section, time)));
            if (list.size() <= 0) {
                logger.debug("票卡客流累计多条件查询失败，结果为空");
            } else {
                logger.debug("票卡客流累计多条件查询成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public Specification<TicketCumulativeFlow> byConditions(List<Integer> ids, List<Integer> lineIds, List<Integer> stationIds, Integer direct, Integer section, Date time) {
        return new Specification<TicketCumulativeFlow>() {
            public Predicate toPredicate(Root<TicketCumulativeFlow> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                logger.debug("ticketSumByConditions请求的参数ids值为:{}", ids);
                if (ids != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(LineTimeShareFlow_.id), ids));
//                    predicate.getExpressions().add(builder.in(root.get(LineTimeShareFlow_.lineId)).in(ids));
                    predicate.getExpressions().add(root.<Integer>get(TicketCumulativeFlow_.ticketId).in(ids));
                }
                logger.debug("ticketSumByConditions请求的参数lineIds值为:{}", lineIds);
                if (lineIds != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(LineTimeShareFlow_.id), ids));
//                    predicate.getExpressions().add(builder.in(root.get(LineTimeShareFlow_.lineId)).in(ids));
                    predicate.getExpressions().add(root.<Integer>get(TicketCumulativeFlow_.lineId).in(lineIds));
                }
                logger.debug("ticketSumByConditions请求的参数stationIds值为:{}", stationIds);
                if (stationIds != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(LineTimeShareFlow_.id), ids));
//                    predicate.getExpressions().add(builder.in(root.get(LineTimeShareFlow_.lineId)).in(ids));
                    predicate.getExpressions().add(root.<Integer>get(TicketCumulativeFlow_.stationId).in(stationIds));
                }
//
                logger.debug("ticketSumByConditions请求的参数direct值为:{}", direct);
                if (direct != null) {
                    predicate.getExpressions().add(builder.equal(root.get(TicketCumulativeFlow_.direction), direct));
                }

                logger.debug("ticketSumByConditions请求的参数section值为:{}", section);
                if (section != null) {
                    predicate.getExpressions().add(builder.equal(root.get(TicketCumulativeFlow_.section), section));
                }
//
                logger.debug("ticketSumByConditions请求的参数time值为:{}", time);
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
                    predicate.getExpressions().add(builder.between(root.get(TicketCumulativeFlow_.timestamp),startTime,endTime));
                }

                return predicate;
            }
        };
    }
    // Dynamic End
}
