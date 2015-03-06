package com.wooplr.persistence.dao;

import com.mongodb.MongoException;
import com.wooplr.persistence.common.BaseMongoDAO;
import com.wooplr.persistence.entity.EventLog;

/**
 * @author subharthi chatterjee
 * 
 */
public interface EventLogDAO extends BaseMongoDAO<EventLog, String> {

	String COLLECTION_NAME = "event_log";
	String TYPE = "type";
	String TIME_INTERVAL_IN_HOURS = "time_interval_in_hours";
	String EXPIRY_DATE = "expiry_date";

	long countEvents(int type, int timeIntervalInHours) throws MongoException;
}
