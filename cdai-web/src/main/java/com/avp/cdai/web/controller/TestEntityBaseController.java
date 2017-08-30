package com.avp.cdai.web.controller;

import com.avp.cdai.web.entity.TestEntityBase;
import com.avp.cdai.web.repository.TestEntityBaseRepository;
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
 * Created by guo on 2017/8/11.
 */
@RestController
public class TestEntityBaseController {
    @Autowired
    TestEntityBaseRepository testEntityBaseRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "test",method = RequestMethod.GET)
    ResponseEntity<RestBody<TestEntityBase>> findAll(){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<TestEntityBase> list = testEntityBaseRepository.findAll();
            builder.setResultEntity(list, ResponseCode.RETRIEVE_SUCCEED);
            logger.debug("查询成功，记录数量为：{}",list.size());
            return builder.getResponseEntity();
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return  builder.getResponseEntity();
    }
}
