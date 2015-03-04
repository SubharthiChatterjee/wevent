package com.wooplr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.wooplr.commons.DataAccessException;
import com.wooplr.persistence.dao.EventCountDAO;
import com.wooplr.persistence.dao.EventDAO;
import com.wooplr.persistence.entity.Event;
import com.wooplr.persistence.entity.EventCount;

/**
 * @author subharthi chatterjee
 * 
 */
@Service
public class EventServiceImpl implements EventService {

	private static final Logger logger = Logger.getLogger(EventServiceImpl.class);
	private EventDAO eventDAO;
	private EventCountDAO eventCountDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.service.EventService#getRecentEvents(java.lang.String,
	 * int)
	 */
	@Override
	public List<Event> getRecentEvents(String lastEventId, int limit, String compare) throws DataAccessException {
		try {
			return eventDAO.getEvent(lastEventId, limit, compare);
		} catch (MongoException e) {
			logger.error("Error occured in getRecentEvents for lastEventId:" + lastEventId + " limit:" + limit, e);
			throw new DataAccessException(e);
		}
	}

	@Async
	private void storeEventCount(long timeIntervalInMillis) throws DataAccessException {

		Map<Integer, Integer> eventCountMap = new HashMap<Integer, Integer>();
		try {
			eventCountMap = eventDAO.countEvents(new Date(new Date().getTime() - timeIntervalInMillis));
		} catch (MongoException e) {
			logger.error("Error occured in getEventCount for timeIntervalInMillis:" + timeIntervalInMillis, e);
			throw new DataAccessException(e);
		}
		List<EventCount> eventCountList = new ArrayList<EventCount>();
		for (Map.Entry<Integer, Integer> eventCountMapEntry : eventCountMap.entrySet()) {
			EventCount eventCount = new EventCount();
			eventCount.setType(eventCountMapEntry.getKey());
			eventCount.setCount(eventCountMapEntry.getValue());
			eventCount.setTimeIntervalInHours(((Long) (timeIntervalInMillis / (60 * 60 * 1000))).intValue());
			eventCount.setCreateDate(new Date());

			eventCountList.add(eventCount);
		}
		try {
			eventCountDAO.upsert(eventCountList);
		} catch (MongoException e) {
			logger.error("Error occured in update for eventCountList:" + eventCountList, e);
			throw new DataAccessException(e);
		}
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
			event = eventDAO.insert(event);

			/* call for 8 hours converted to timeInMillis */
			storeEventCount(8 * 60 * 60 * 1000);
			/* call for 24 hours converted to timeInMillis */
			storeEventCount(24 * 60 * 60 * 1000);
			/* call for 7 days converted to timeInMillis */
			storeEventCount(7 * 24 * 60 * 60 * 1000);
			return event;
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
	 * @see com.wooplr.service.EventService#getTopEvents(int)
	 */
	@Override
	public List<EventCount> getTopEvents(int timeInterval) throws DataAccessException {
		try {
			return eventCountDAO.getTopEvents(timeInterval);
		} catch (MongoException e) {
			logger.error("Error occured in getTopEvents for timeInterval:" + timeInterval, e);
			throw new DataAccessException(e);
		}
	}

	/**
	 * @param eventCountDAO
	 *            the eventCountDAO to set
	 */
	public void setEventCountDAO(EventCountDAO eventCountDAO) {
		this.eventCountDAO = eventCountDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.service.EventService#getEventCount(int)
	 */
	@Override
	public List<EventCount> getEventCount(int timeInterVal) throws DataAccessException {
		try {
			return eventCountDAO.getEventCount(timeInterVal);
		} catch (MongoException e) {
			logger.error("Error occured in getTopEvents for timeInterval:" + timeInterVal, e);
			throw new DataAccessException(e);
		}
	}
}
