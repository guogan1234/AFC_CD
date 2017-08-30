package com.avp.cdai.web.repository;

import com.avp.cdai.web.entity.TicketCumulativeFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by guo on 2017/8/17.
 */
public interface TicketCumulativeFlowRepository extends JpaRepository<TicketCumulativeFlow,Integer>,JpaSpecificationExecutor<TicketCumulativeFlow> {
    @Query("select new TicketCumulativeFlow(t.ticketId,sum(t.flowCount)) from TicketCumulativeFlow t where t.section=:section and t.direction=:direct and t.timestamp>=:startTime and t.timestamp<=:endTime group by t.ticketId order by t.ticketId asc")
    List<TicketCumulativeFlow> getData(@Param("direct") Integer direct, @Param("section") Integer section, @Param("startTime")Date startTime,@Param("endTime")Date endTime);
}
