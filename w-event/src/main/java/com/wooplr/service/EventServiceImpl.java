package com.wooplr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.wooplr.commons.DataAccessException;
import com.wooplr.commons.EventType;
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
	 * @see com.wooplr.service.EventService#getEventCount(long)
	 */
	@Override
	public Map<Integer, Integer> getEventCount(long timeIntervalInMillis) throws DataAccessException {

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
		}
		return eventCountMap;
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
	public List<Integer> getTopEvents(long timeIntervalInMillis) throws DataAccessException {
		try {
			List<Map.Entry<Integer, List<Integer>>> topEventList = eventDAO.getTopEvents(new Date(new Date().getTime()
					- timeIntervalInMillis));

			List<Integer> eventTypeList = new ArrayList<Integer>(EventType.EVENT_TYPES);
			List<Integer> topEventsList = new ArrayList<Integer>();

			for (int i = topEventList.size() - 1; i >= 0; i--) {
				List<Integer> values = topEventList.get(i).getValue();
				boolean removed = eventTypeList.removeAll(values);

				if (removed) {
					values.removeAll(topEventsList);
					topEventsList.addAll(values);
				}

				if (eventTypeList.isEmpty()) {
					break;
				}
			}
			return topEventsList;

		} catch (MongoException e) {
			logger.error("Error occured in getTopEvents for timeIntervalInMillis:" + timeIntervalInMillis, e);
			throw new DataAccessException(e);
		}
	}
}
