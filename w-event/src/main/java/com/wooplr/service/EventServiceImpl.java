/**
 * Copyright (c) 2011 by InfoArmy Inc.  All Rights Reserved.
 * This file contains proprietary information of InfoArmy Inc.
 * Copying, use, reverse engineering, modification or reproduction of
 * this file without prior written approval is prohibited.
 *
 */
package com.wooplr.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.wooplr.commons.DataAccessException;
import com.wooplr.persistence.dao.EventDAO;
import com.wooplr.persistence.entity.Event;

/**
 * @author subharthi chatterjee
 * 
 */
@Service
public class EventServiceImpl implements EventService {

	private static final Logger logger = Logger.getLogger(EventServiceImpl.class);
	private EventDAO eventDAO;
	private Map<Long, Map<Integer, Integer>> eventTimeIntervalCountMap = Collections
			.synchronizedMap(new HashMap<Long, Map<Integer, Integer>>());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.service.EventService#getRecentEvents(java.lang.String,
	 * int)
	 */
	@Override
	public List<Event> getRecentEvents(String lastEventId, int limit) throws DataAccessException {
		try {
			return eventDAO.getEvent(lastEventId, limit);
		} catch (MongoException e) {
			logger.error("Error occured in getRecentEvents for lastEventId:" + lastEventId + " limit:" + limit, e);
			throw new DataAccessException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.service.EventService#getEventCount(long)
	 */
	@Override
	public Map<Integer, Integer> getEventCount(long timeIntervalInMillis) throws DataAccessException {

		if (eventTimeIntervalCountMap.isEmpty() || (eventTimeIntervalCountMap.get(timeIntervalInMillis) == null)) {
			Map<Integer, Integer> eventCountMap = new HashMap<Integer, Integer>();
			try {
				eventCountMap = eventDAO.countEvents(new Date(new Date().getTime() - timeIntervalInMillis));
			} catch (MongoException e) {
				logger.error("Error occured in getEventCount for timeIntervalInMillis:" + timeIntervalInMillis, e);
				throw new DataAccessException(e);
			}
			for (Map.Entry<Integer, Integer> eventCountMapEntry : eventCountMap.entrySet()) {
				HashMap<Integer, Integer> eventTypeCountMap = new HashMap<Integer, Integer>();
				eventTypeCountMap.put(eventCountMapEntry.getKey(), eventCountMapEntry.getValue());
				eventTimeIntervalCountMap.put(timeIntervalInMillis, eventTypeCountMap);
			}
		}
		return eventTimeIntervalCountMap.get(timeIntervalInMillis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wooplr.service.EventService#addEvent(com.wooplr.persistence.entity
	 * .Event)
	 */
	@Override
	public Event addEvent(int eventType, String value1, String value2) throws DataAccessException {
		Event event = new Event();
		try {
			event.setType(eventType);
			event.setValue1(value1);
			event.setValue2(value2);
			event.setCreateDate(new Date());
			return eventDAO.insert(event);
		} catch (MongoException e) {
			logger.error("Error occured in addEvent for event:" + event);
			throw new DataAccessException(e);
		}
	}

	/**
	 * @return the eventDAO
	 */
	public EventDAO getEventDAO() {
		return eventDAO;
	}

	/**
	 * @param eventDAO
	 *            the eventDAO to set
	 */
	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.service.EventService#getTopEvents(java.lang.String, int,
	 * long)
	 */
	@Override
	public List<Event> getTopEvents(String lastEventId, int limit, long timeIntervalInMillis)
			throws DataAccessException {
		try {
			return eventDAO.getEvent(lastEventId, limit, new Date(new Date().getTime() - timeIntervalInMillis));
		} catch (MongoException e) {
			logger.error("Error occured in getRecentEvents for lastEventId:" + lastEventId + " limit:" + limit, e);
			throw new DataAccessException(e);
		}
	}
}
