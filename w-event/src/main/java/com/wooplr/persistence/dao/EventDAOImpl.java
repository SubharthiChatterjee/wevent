/**
 * Copyright (c) 2011 by InfoArmy Inc.  All Rights Reserved.
 * This file contains proprietary information of InfoArmy Inc.
 * Copying, use, reverse engineering, modification or reproduction of
 * this file without prior written approval is prohibited.
 *
 */
package com.wooplr.persistence.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.wooplr.persistence.common.AbstractMongoDAO;
import com.wooplr.persistence.entity.Event;

/**
 * @author subharthi chatterjee
 * 
 */
public class EventDAOImpl extends AbstractMongoDAO<Event, String> implements EventDAO {

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
	protected Event map(DBObject dbObject) {
		Event event = new Event();
		event.setId(dbObject.get(ID).toString());
		event.setType((Integer) dbObject.get(TYPE));
		event.setValue1((String) dbObject.get(VALUE1));
		event.setValue2((String) dbObject.get(VALUE2));
		event.setCreateDate((Date) dbObject.get(CREATE_DATE));
		return event;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wooplr.persistence.common.AbstractMongoDAO#map(com.wooplr.persistence
	 * .common.MongoEntity)
	 */
	@Override
	protected DBObject map(Event entity) {
		DBObject dbObject = new BasicDBObject();
		dbObject.put(ID, entity.getId());
		dbObject.put(TYPE, entity.getType());
		dbObject.put(VALUE1, entity.getValue1());
		dbObject.put(VALUE2, entity.getValue2());
		dbObject.put(CREATE_DATE, entity.getCreateDate());
		return dbObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.dao.EventDAO#getEvent(java.lang.String, int)
	 */
	@Override
	public List<Event> getEvent(String lastEventId, int limit) throws MongoException {

		DBObject dbQuery = new BasicDBObject();
		if ((lastEventId != null) && (lastEventId.trim().length() > 0)) {
			dbQuery.put(ID, new BasicDBObject("$gt", lastEventId));
		}
		DBCursor cursor = getDB().getCollection(COLLECTION_NAME).find(dbQuery).limit(limit);
		List<Event> events = new ArrayList<>();

		for (DBObject object : cursor) {
			events.add(map(object));
		}
		return events;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.dao.EventDAO#countEvents(java.util.Date)
	 */
	@Override
	public Map<Integer, Integer> countEvents(Date greaterThanDate) throws MongoException {
		DBObject queryObj = new BasicDBObject(CREATE_DATE, new BasicDBObject("$gt", greaterThanDate));
		DBObject projObj = new BasicDBObject(TYPE, 1);

		DBCursor cursor = getDB().getCollection(COLLECTION_NAME).find(queryObj, projObj);

		Map<Integer, Integer> returnMap = new HashMap<Integer, Integer>();

		for (DBObject obj : cursor) {
			int eventType = (Integer) obj.get(TYPE);
			Integer eventCount = returnMap.get(eventType);

			if (eventCount == null) {
				returnMap.put(eventType, eventCount);
				continue;
			}
			eventCount++;
			returnMap.put(eventType, eventCount);
		}
		return returnMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.dao.EventDAO#getEvent(java.lang.String, int,
	 * java.util.Date)
	 */
	@Override
	public List<Event> getEvent(String lastEventId, int limit, Date greaterThanDate) throws MongoException {
		DBObject dbQuery = new BasicDBObject();
		if ((lastEventId != null) && (lastEventId.trim().length() > 0)) {
			dbQuery.put(ID, new BasicDBObject("$gt", lastEventId));
		}
		dbQuery.put(CREATE_DATE, new BasicDBObject("$gt", greaterThanDate));
		DBCursor cursor = getDB().getCollection(COLLECTION_NAME).find(dbQuery).limit(limit);
		List<Event> events = new ArrayList<>();

		for (DBObject object : cursor) {
			events.add(map(object));
		}
		return events;
	}
}
