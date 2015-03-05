package com.wooplr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;

import com.mongodb.MongoException;
import com.wooplr.commons.DataAccessException;
import com.wooplr.persistence.dao.EventCountDAO;
import com.wooplr.persistence.dao.EventDAO;
import com.wooplr.persistence.entity.EventCount;

/**
 * @author subharthi chatterjee
 * 
 */
public class EventAsyncServiceImpl {

	private EventCountDAO eventCountDAO;
	private EventDAO eventDAO;
	private static final Logger logger = Logger.getLogger(EventAsyncServiceImpl.class);

	@Async
	public void updateEventCount(int eventType, int timeIntervalInHours) throws DataAccessException {
		EventCount eventCount = eventCountDAO.getEventCount(eventType, timeIntervalInHours);
		long timeIntervalInMillis = timeIntervalInHours * 60 * 60 * 1000;
		if (eventCount == null) {
			storeEventCount(timeIntervalInMillis);
			return;
		}
		if ((new Date().getTime() - timeIntervalInMillis) <= eventCount.getCreateDate().getTime()) {
			eventCountDAO.updateEventCount(eventCount.getId(), eventCount.getCount() + 1);
		}
	}

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

	/**
	 * @return the eventCountDAO
	 */
	public EventCountDAO getEventCountDAO() {
		return eventCountDAO;
	}

	/**
	 * @param eventCountDAO
	 *            the eventCountDAO to set
	 */
	public void setEventCountDAO(EventCountDAO eventCountDAO) {
		this.eventCountDAO = eventCountDAO;
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
}
