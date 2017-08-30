package com.avp.cdai.web.repository;

import com.avp.cdai.web.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by guo on 2017/8/16.
 */
public interface TicketTypeRepository extends JpaRepository<TicketType,Integer>,JpaSpecificationExecutor<TicketType> {
}
