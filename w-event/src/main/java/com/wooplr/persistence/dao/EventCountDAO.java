package com.wooplr.persistence.dao;

import java.util.List;

import com.mongodb.MongoException;
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

	List<EventCount> getEventCount(int timeIntervalInHours) throws MongoException;

	EventCount getEventCount(int eventType, int timeIntervalInHours) throws MongoException;

	List<EventCount> getTopEvents(int timeIntervalInHours) throws MongoException;

	public void updateEventCount(String id, int count) throws MongoException;

}
