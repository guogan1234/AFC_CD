package com.avp.cdai.web.controller;

import com.avp.cdai.web.entity.LineTimeShareFlow;
import com.avp.cdai.web.entity.LineTimeShareFlow_;
import com.avp.cdai.web.repository.LineTimeShareFlowRepository;
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
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by pw on 2017/8/8.
 */
@RestController
public class LineTimeShareFlowController {
    @Autowired
    LineTimeShareFlowRepository lineTimeShareFlowRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "lineShareFlow",method = RequestMethod.GET)
    ResponseEntity<RestBody<LineTimeShareFlow>> findAll(){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
           List<LineTimeShareFlow> lineShareFlows = lineTimeShareFlowRepository.findAll();
            if(lineShareFlows != null) {
                builder.setResultEntity(lineShareFlows, ResponseCode.RETRIEVE_SUCCEED);
                logger.debug("线路分时客流数量为：{}",lineShareFlows.size());
                return builder.getResponseEntity();
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "findByConditions",method = RequestMethod.GET)
    ResponseEntity<RestBody<LineTimeShareFlow>> findByConditions(@RequestParam("ids") List<Integer> ids,@RequestParam(value = "direct",required = false) Integer direct, Date time){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<LineTimeShareFlow> list = lineTimeShareFlowRepository.findAll(where(byConditions(ids, direct, time)));
            if (list.size() <= 0) {
                logger.debug("线路客流分时多条件查询失败，结果为空");
            } else {
                logger.debug("线路客流分时多条件查询成功，数据量为:({})", list.size());
            }
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    // Dynamic Query Utils
    public Specification<LineTimeShareFlow> byConditions(List<Integer> ids, Integer direct, Date time) {
        return new Specification<LineTimeShareFlow>() {
            public Predicate toPredicate(Root<LineTimeShareFlow> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();

                logger.debug("inventories/findByConditions请求的参数ids值为:{}", ids);
                if (ids != null) {
//                    predicate.getExpressions().add(builder.equal(root.get(LineTimeShareFlow_.id), ids));
//                    predicate.getExpressions().add(builder.in(root.get(LineTimeShareFlow_.lineId)).in(ids));
                    predicate.getExpressions().add(root.<Integer>get(LineTimeShareFlow_.lineId).in(ids));
                }
//
//                logger.debug("inventories/findByConditions请求的参数direct值为:{}", direct);
                if (direct != null) {
                    predicate.getExpressions().add(builder.equal(root.get(LineTimeShareFlow_.direction), direct));
                }
//
                logger.debug("inventories/findByConditions请求的参数time值为:{}", time);
                if (time != null) {
                    predicate.getExpressions().add(builder.equal(root.get(LineTimeShareFlow_.timestamp), time));
                }

                return predicate;
            }
        };
    }
    // Dynamic End
}
