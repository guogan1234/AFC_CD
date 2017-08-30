package com.avp.cdai.web.controller;

import com.avp.cdai.web.entity.ObjStation;
import com.avp.cdai.web.repository.ObjStationRepository;
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
 * Created by guo on 2017/8/8.
 */
@RestController
public class ObjStationController {
    @Autowired
    ObjStationRepository objStationRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "stations",method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjStation>> findAll(){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try {
            List<ObjStation> stations = objStationRepository.findAll();
            if (stations != null) {
                builder.setResultEntity(stations, ResponseCode.RETRIEVE_SUCCEED);
                logger.debug("站点的数量为：{}",stations.size());
                return builder.getResponseEntity();
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }

        return builder.getResponseEntity();
    }

    @RequestMapping(value = "stations/{id}/lines/{lineId}",method = RequestMethod.GET)
    ResponseEntity<RestBody<ObjStation>> findById(@PathVariable("id") Integer id,@PathVariable("lineId") Integer lineId){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try{
            ObjStation station = objStationRepository.findBystationIdAndLineId(id,lineId);
//            ObjStation station = null;
            if(station != null) {
                builder.setResultEntity(station, ResponseCode.RETRIEVE_SUCCEED);
                logger.debug("已找到对应站点,站点名称--{}！",station.getStationName());
                return builder.getResponseEntity();
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }

    @RequestMapping(value = "stations/findbyname",method = RequestMethod.POST)
    ResponseEntity<RestBody<ObjStation>> findByName(@RequestParam("stationName") String name){
        ResponseBuilder builder = ResponseBuilder.createBuilder();
        try{
            ObjStation station = objStationRepository.findBystationName(name);
//            ObjStation station = null;
            logger.debug("已找到对应站点，站点名称--{}",station.getStationName());
            builder.setResultEntity(station,ResponseCode.RETRIEVE_SUCCEED);
        }catch (Exception e){
            logger.error(e.getMessage());
            builder.setErrorCode(ResponseCode.RETRIEVE_FAILED);
        }
        return builder.getResponseEntity();
    }
}
