package com.avp.cdai.web.controller;

import com.avp.cdai.web.entity.StationShareFlow;
import com.avp.cdai.web.repository.StationShareFlowRepository;
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
 * Created by pw on 2017/8/11.
 */
@RestController
public class StationShareFlowController {
    @Autowired
    StationShareFlowRepository stationShareFlowRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "",method = RequestMethod.GET)
    ResponseEntity<RestBody<StationShareFlow>> findAll(){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<StationShareFlow> list = stationShareFlowRepository.findAll();
            logger.debug("车站客流分时记录查询成功，数量为{}",list.size());
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }

        return builder.getResponseEntity();
    }
}
