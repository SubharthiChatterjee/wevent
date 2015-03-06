package com.wooplr.persistence.dao;

import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.wooplr.persistence.common.AbstractMongoDAO;
import com.wooplr.persistence.entity.EventLog;

/**
 * @author subharthi chatterjee
 * 
 */
public class EventLogDAOImpl extends AbstractMongoDAO<EventLog, String> implements EventLogDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.common.BaseMongoDAO#upsert(java.util.List)
	 */
	@Override
	public List<EventLog> upsert(List<EventLog> entity) throws MongoException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.common.AbstractMongoDAO#getCollectionName()
	 */
	@Override
	protected String getCollectionName() {
		return COLLECTION_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wooplr.persistence.common.AbstractMongoDAO#map(com.mongodb.DBObject)
	 */
	@Override
	protected EventLog map(DBObject dbObject) {
		EventLog eventLog = new EventLog();
		eventLog.setId(dbObject.get(ID).toString());
		eventLog.setTimeInterval((Integer) dbObject.get(TIME_INTERVAL_IN_HOURS));
		eventLog.setType((Integer) dbObject.get(TYPE));
		eventLog.setCreateDate((Date) dbObject.get(CREATE_DATE));
		return eventLog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wooplr.persistence.common.AbstractMongoDAO#map(com.wooplr.persistence
	 * .common.MongoEntity)
	 */
	@Override
	protected DBObject map(EventLog entity) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(ID, entity.getId());
		dbObject.put(TIME_INTERVAL_IN_HOURS, entity.getTimeInterval());
		dbObject.put(TYPE, entity.getType());
		dbObject.put(CREATE_DATE, entity.getCreateDate());
		dbObject.put(EXPIRY_DATE, entity.getExpireTime());
		return dbObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.dao.EventLogDAO#countEvents(int, int)
	 */
	@Override
	public long countEvents(int type, int timeIntervalInHours) throws MongoException {
		return getDB().getCollection(getCollectionName()).count(
				new BasicDBObject(TYPE, type).append(TIME_INTERVAL_IN_HOURS, timeIntervalInHours));
	}

}
