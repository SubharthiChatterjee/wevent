package com.wooplr.persistence.dao;

import java.util.List;

import com.wooplr.persistence.common.BaseMongoDAO;
import com.wooplr.persistence.entity.EventCount;

/**
 * @author subharthi chatterjee
 * 
 */
public interface EventCountDAO extends BaseMongoDAO<EventCount, String> {

	String COLLECTION_NAME = "event_count";
	String TYPE = "type";
	String COUNT = "count";
	String TIME_INTERVAL_IN_HOURS = "time_interval_in_hours";

	List<EventCount> getEventCount(int timeIntervalInHours);

	List<EventCount> getTopEvents(int timeIntervalInHours);

}
