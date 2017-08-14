package com.avp.cdai.web.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StationCumulativeFlow.class)
public abstract class StationCumulativeFlow_ {

	public static volatile SingularAttribute<StationCumulativeFlow, Date> insertTime;
	public static volatile SingularAttribute<StationCumulativeFlow, Integer> lineId;
	public static volatile SingularAttribute<StationCumulativeFlow, Integer> flowCount;
	public static volatile SingularAttribute<StationCumulativeFlow, Integer> section;
	public static volatile SingularAttribute<StationCumulativeFlow, String> stationName;
	public static volatile SingularAttribute<StationCumulativeFlow, Integer> id;
	public static volatile SingularAttribute<StationCumulativeFlow, Date> flowTime;
	public static volatile SingularAttribute<StationCumulativeFlow, Integer> stationId;
	public static volatile SingularAttribute<StationCumulativeFlow, Integer> direction;

}

