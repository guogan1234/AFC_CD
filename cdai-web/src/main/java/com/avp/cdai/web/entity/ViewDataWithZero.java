package com.avp.cdai.web.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by guo on 2017/8/29.
 */
public class ViewDataWithZero<T,S> {
    List<Map<Date,T>> flowWithTime;
    List<S> objList;
}
