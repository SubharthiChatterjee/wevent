package com.wooplr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.wooplr.commons.DataAccessException;
import com.wooplr.commons.EventTimeInterval;
import com.wooplr.persistence.dao.EventCountDAO;
import com.wooplr.persistence.dao.EventDAO;
import com.wooplr.persistence.dao.EventLogDAO;
import com.wooplr.persistence.entity.Event;
import com.wooplr.persistence.entity.EventCount;
import com.wooplr.persistence.entity.EventLog;

/**
 * @author subharthi chatterjee
 * 
 */
@Service
public class EventServiceImpl implements EventService {

	private static final Logger logger = Logger.getLogger(EventServiceImpl.class);
	private EventDAO eventDAO;
	private EventCountDAO eventCountDAO;
	private EventAsyncServiceImpl eventAsyncService;
	private EventLogDAO eventLogDAO;

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

			List<EventLog> eventLogs = new ArrayList<EventLog>();
			for (int timeInterval : EventTimeInterval.getTimeIntervalsInHours()) {
				EventLog eventLog = new EventLog();
				eventLog.setCreateDate(event.getCreateDate());
				eventLog.setEventId(event.getId());
				eventLog.setTimeInterval(timeInterval);
				eventLogs.add(eventLog);
			}
			eventLogDAO.insert(eventLogs);

			for (int timeInterval : EventTimeInterval.getTimeIntervalsInHours()) {
				eventAsyncService.updateEventCount(eventType, timeInterval);
			}
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

	/**
	 * @param eventAsyncService
	 *            the eventAsyncService to set
	 */
	public void setEventAsyncService(EventAsyncServiceImpl eventAsyncService) {
		this.eventAsyncService = eventAsyncService;
	}

	/**
	 * @param eventLogDAO
	 *            the eventLogDAO to set
	 */
	public void setEventLogDAO(EventLogDAO eventLogDAO) {
		this.eventLogDAO = eventLogDAO;
	}
}
