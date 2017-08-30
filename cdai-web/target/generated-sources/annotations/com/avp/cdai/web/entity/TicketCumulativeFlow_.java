package com.avp.cdai.web.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TicketCumulativeFlow.class)
public abstract class TicketCumulativeFlow_ {

	public static volatile SingularAttribute<TicketCumulativeFlow, Date> insertTime;
	public static volatile SingularAttribute<TicketCumulativeFlow, Integer> lineId;
	public static volatile SingularAttribute<TicketCumulativeFlow, Integer> section;
	public static volatile SingularAttribute<TicketCumulativeFlow, Integer> flowCount;
	public static volatile SingularAttribute<TicketCumulativeFlow, Integer> id;
	public static volatile SingularAttribute<TicketCumulativeFlow, Integer> ticketId;
	public static volatile SingularAttribute<TicketCumulativeFlow, Integer> stationId;
	public static volatile SingularAttribute<TicketCumulativeFlow, Integer> direction;
	public static volatile SingularAttribute<TicketCumulativeFlow, Date> timestamp;

}

