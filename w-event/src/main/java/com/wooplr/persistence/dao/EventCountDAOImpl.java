package com.wooplr.persistence.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.wooplr.persistence.common.AbstractMongoDAO;
import com.wooplr.persistence.entity.EventCount;

/**
 * @author subharthi chatterjee
 * 
 */
public class EventCountDAOImpl extends AbstractMongoDAO<EventCount, String> implements EventCountDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.dao.EventCountDAO#getEventCount(int)
	 */
	@Override
	public List<EventCount> getEventCount(int timeIntervalInHours) {
		DBObject queryObj = new BasicDBObject(TIME_INTERVAL_IN_HOURS, timeIntervalInHours);
		DBObject sortObject = new BasicDBObject(TYPE, 1);

		DBCursor dbCur = getDB().getCollection(getCollectionName()).find(queryObj).sort(sortObject);

		List<EventCount> eventCountList = new ArrayList<EventCount>();
		for (DBObject dbObject : dbCur) {
			eventCountList.add(map(dbObject));
		}
		return eventCountList;
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
	protected EventCount map(DBObject dbObject) {
		if (dbObject == null) {
			return null;
		}
		EventCount eventCount = new EventCount();
		eventCount.setId(dbObject.get(ID).toString());
		eventCount.setCount((Integer) dbObject.get(COUNT));
		eventCount.setTimeIntervalInHours((Integer) dbObject.get(TIME_INTERVAL_IN_HOURS));
		eventCount.setType((Integer) dbObject.get(TYPE));
		eventCount.setCreateDate((Date) dbObject.get(CREATE_DATE));
		return eventCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wooplr.persistence.common.AbstractMongoDAO#map(com.wooplr.persistence
	 * .common.MongoEntity)
	 */
	@Override
	protected DBObject map(EventCount entity) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(ID, entity.getId());
		dbObject.put(COUNT, entity.getCount());
		dbObject.put(TIME_INTERVAL_IN_HOURS, entity.getTimeIntervalInHours());
		dbObject.put(TYPE, entity.getType());
		dbObject.put(CREATE_DATE, entity.getCreateDate());
		return dbObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.dao.EventCountDAO#getTopEvents(int)
	 */
	@Override
	public List<EventCount> getTopEvents(int timeIntervalInHours) {
		DBObject queryObj = new BasicDBObject(TIME_INTERVAL_IN_HOURS, timeIntervalInHours);
		DBObject sortObject = new BasicDBObject(COUNT, -1);

		DBCursor dbCur = getDB().getCollection(getCollectionName()).find(queryObj).sort(sortObject);

		List<EventCount> eventCountList = new ArrayList<EventCount>();
		for (DBObject dbObject : dbCur) {
			eventCountList.add(map(dbObject));
		}
		return eventCountList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.common.BaseMongoDAO#upsert(java.util.List)
	 */
	@Override
	public List<EventCount> upsert(List<EventCount> entities) throws MongoException {
		for (EventCount entity : entities) {
			DBObject queryObj = new BasicDBObject(ID, entity.getId());
			DBObject setObject = new BasicDBObject("$set", new BasicDBObject(TYPE, entity.getType())
					.append(COUNT, entity.getCount()).append(TIME_INTERVAL_IN_HOURS, entity.getTimeIntervalInHours())
					.append(CREATE_DATE, new Date()));
			getDB().getCollection(getCollectionName()).update(queryObj, setObject, true, false);
		}
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.dao.EventCountDAO#getEventCount(int, int)
	 */
	@Override
	public EventCount getEventCount(int eventType, int timeIntervalInHours) throws MongoException {

		DBObject queryObj = new BasicDBObject(ID, eventType + "_" + timeIntervalInHours);
		DBObject dbObject = getDB().getCollection(getCollectionName()).findOne(queryObj);

		return map(dbObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wooplr.persistence.dao.EventCountDAO#updateEventCount(java.lang.String
	 * , int)
	 */
	@Override
	public void updateEventCount(String id, int count) throws MongoException {
		getDB().getCollection(getCollectionName()).update(new BasicDBObject(ID, id),
				new BasicDBObject("$set", new BasicDBObject(COUNT, count)));
	}
}
