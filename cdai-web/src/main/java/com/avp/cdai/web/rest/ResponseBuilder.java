package com.avp.cdai.web.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by boris feng on 2017/5/11.
 *
 * ResponseBuilder<T> builer = ResponseBuilder.createBuilder(request);
 * builder.setResultEntity(entity, ResponseCode.OK);
 * return builder.getResponseEntity();
 *
 */
public class ResponseBuilder<T> {

    private ResponseEntity<RestBody<T>> response
            = new ResponseEntity<RestBody<T>>(new RestBody<T>(ResponseCode.FAILED), HttpStatus.INTERNAL_SERVER_ERROR);

    class EntityBody<T> extends RestBody<T> {
        private T result;

        EntityBody(ResponseCode code) {
            super(code);
        }

        EntityBody(T result, ResponseCode code) {
            super(code);
            this.result = result;
        }

        @JsonProperty("result")
        public T getResult() {
            return this.result;
        }
    }

    class ListBody<T> extends EntityBody<T> {
        private List<T> results = new ArrayList<T>();

        ListBody(ResponseCode code) {
            super(code);
        }

        ListBody(List<T> results, ResponseCode code) {
            super(code);
            this.results.addAll(results);
        }

        @Override
        @JsonIgnore
        public T getResult() {
            return null;
        }

        @JsonProperty(value = "results")
        public List<T> getResults() {
            return this.results;
        }
    }

    class PageBody<T> extends ListBody<T> {
        private Page<T> page;

        PageBody(Page<T> page, ResponseCode code) {
            super(code);

            this.page = page;
        }

        @Override
        @JsonProperty(value = "results")
        public List<T> getResults() {
            return this.page.getContent();
        }

        @JsonProperty(value = "page")
        public Map<String, Object> getPage() {
            Map<String, Object> page = new HashMap<>();
            page.put("size", this.page.getSize());
            page.put("totalElements", this.page.getTotalElements());
            page.put("totalPages", this.page.getTotalPages());
            page.put("number", this.page.getNumber());
            page.put("hasNext", this.page.hasNext());
            page.put("hasPrevious", this.page.hasPrevious());
            return page;
        }
    }


    public static <T> ResponseBuilder createBuilder() {
        return new ResponseBuilder<T>();
    }

    public ResponseEntity<RestBody<T>> getResponseEntity() {
        return this.response;
    }

    public void setErrorCode(ResponseCode code) {
        this.response = new ResponseEntity<>(new RestBody(code), getHttpStatus(code));
    }

    public void setErrorCode(ResponseCode code, String message) {
        this.setErrorCode(code);
        this.response.getBody().setMessage(message);
    }

    public <T> ResponseBuilder setResultEntity(T entity) {
        return this.setResultEntity(entity, ResponseCode.OK);
    }

    public <T> ResponseBuilder setResultEntity(T entity, ResponseCode code) {
        this.response = entity == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity(new EntityBody<T>(entity, code), getHttpStatus(code));

        return this;
    }

    public <T> ResponseBuilder setResultEntity(List<T> entities) {
        return this.setResultEntity(entities, ResponseCode.OK);
    }

    public <T> ResponseBuilder setResultEntity(List<T> entities, ResponseCode code) {
        this.response = new ResponseEntity(new ListBody<T>(entities, code), getHttpStatus(code));
        return this;
    }

    public <T> ResponseBuilder setResultEntity(Page<T> page, ResponseCode code) {
        this.response = new ResponseEntity(new PageBody<>( page, code), getHttpStatus(code));
        return this;
    }

    public <T> ResponseBuilder setResultEntity(Page<T> page) {
        return this.setResultEntity(page, ResponseCode.OK);
    }

    private HttpStatus getHttpStatus(ResponseCode code) {
        if (!_router.containsKey(code))
            return HttpStatus.INTERNAL_SERVER_ERROR;

        return _router.get(code);
    }

    static private Map<ResponseCode, HttpStatus> _router = new HashMap<ResponseCode, HttpStatus>() {
        {
            put(ResponseCode.OK, HttpStatus.OK);
            put(ResponseCode.FAILED, HttpStatus.INTERNAL_SERVER_ERROR);

            put(ResponseCode.CREATE_SUCCEED, HttpStatus.OK);
            put(ResponseCode.CREATE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);

            put(ResponseCode.DELETE_SUCCEED, HttpStatus.OK);
            put(ResponseCode.DELETE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);

            put(ResponseCode.UPDATE_SUCCEED, HttpStatus.OK);
            put(ResponseCode.UPDATE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);

            put(ResponseCode.RETRIEVE_SUCCEED, HttpStatus.OK);
            put(ResponseCode.RETRIEVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);

            put(ResponseCode.ALREADY_EXIST, HttpStatus.BAD_REQUEST);
            put(ResponseCode.NOT_EXIST, HttpStatus.BAD_REQUEST);
            put(ResponseCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
            put(ResponseCode.PARAM_MISSING, HttpStatus.BAD_REQUEST);
        }
    };
}
