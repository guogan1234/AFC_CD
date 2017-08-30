package com.avp.cdai.web.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LineCumulativeFlow.class)
public abstract class LineCumulativeFlow_ {

	public static volatile SingularAttribute<LineCumulativeFlow, Date> insertTime;
	public static volatile SingularAttribute<LineCumulativeFlow, Integer> passengerFlow;
	public static volatile SingularAttribute<LineCumulativeFlow, Integer> lineId;
	public static volatile SingularAttribute<LineCumulativeFlow, Integer> section;
	public static volatile SingularAttribute<LineCumulativeFlow, Integer> id;
	public static volatile SingularAttribute<LineCumulativeFlow, Date> flowTime;
	public static volatile SingularAttribute<LineCumulativeFlow, Integer> direction;

}

