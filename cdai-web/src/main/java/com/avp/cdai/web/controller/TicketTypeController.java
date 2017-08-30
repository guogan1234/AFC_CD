package com.avp.cdai.web.controller;

import com.avp.cdai.web.entity.TicketType;
import com.avp.cdai.web.repository.TicketTypeRepository;
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
 * Created by guo on 2017/8/16.
 */
@RestController
public class TicketTypeController {
    @Autowired
    TicketTypeRepository ticketTypeRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "tickets",method = RequestMethod.GET)
    ResponseEntity<RestBody<TicketType>> findAll(){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<TicketType> list = ticketTypeRepository.findAll();
            logger.debug("票卡类型记录，数量为：{}",list.size());
            builder.setResultEntity(list,ResponseCode.RETRIEVE_SUCCEED);
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }
}
