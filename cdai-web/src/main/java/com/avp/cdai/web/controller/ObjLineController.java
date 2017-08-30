package com.avp.cdai.web.controller;

import com.avp.cdai.web.entity.ObjLine;
import com.avp.cdai.web.repository.ObjLineRepository;
import com.avp.cdai.web.rest.ResponseBuilder;
import com.avp.cdai.web.rest.ResponseCode;
import com.avp.cdai.web.rest.RestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by guo on 2017/8/4.
 */
@RestController
public class ObjLineController {
    @Autowired
    ObjLineRepository objLineRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "lines",method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjLine>> findAll(){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<ObjLine> stations = objLineRepository.findAll();
            logger.debug("查询站点成功,数据量为:{}", stations.size());
//            System.out.println("查询站点成功,数据量为:" + stations.size());
            for(int i = 0;i<stations.size();i++){
                ObjLine obj = stations.get(i);
            }
            builder.setResultEntity(stations, ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        } catch (Exception e) {
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "lines/{id}",method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjLine>> findById(@PathVariable("id") Integer idParam){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            ObjLine objLine = objLineRepository.findById(idParam);
            if(objLine != null){
                builder.setResultEntity(objLine,ResponseCode.RETRIEVE_SUCCEED);
                return builder.getResponseEntity();
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "lines/findByName",method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjLine>> findByName(@RequestParam("linename") String lineName){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            ObjLine objLine = objLineRepository.findBylineName(lineName);
            builder.setResultEntity(objLine,ResponseCode.RETRIEVE_SUCCEED);
            return builder.getResponseEntity();
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }
}
