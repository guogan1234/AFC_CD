package com.avp.cdai.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guo on 2017/8/15.
 */
public class GetDayStartEndDate {
    public static void CreateStartEndDate(Date start,Date end,Date time){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String strStart = format1.format(time);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String strEnd = format2.format(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("start:"+strStart + ",end:"+strEnd);
//        Date startTime = null;
//        Date endTime = null;
        try {
            start = format.parse(strStart);
            end = format.parse(strEnd);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
