package com.avp.cdai.web.controller;

import com.avp.cdai.web.entity.LineTimeShareFlow;
import com.avp.cdai.web.repository.LineTimeShareFlowRepository;
import com.avp.cdai.web.rest.ResponseBuilder;
import com.avp.cdai.web.rest.ResponseCode;
import com.avp.cdai.web.rest.RestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
