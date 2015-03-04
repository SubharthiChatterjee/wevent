package com.wooplr.service;

import java.util.List;

import com.wooplr.commons.DataAccessException;
import com.wooplr.persistence.entity.Event;
import com.wooplr.persistence.entity.EventCount;

/**
 * @author subharthi chatterjee
 * 
 */
public interface EventService {

	/**
	 * @param event
	 * @return
	 * @throws DataAccessException
	 */
	Event addEvent(int eventType, String value1, String value2) throws DataAccessException;

	/**
	 * @param lastEventId
	 * @param limit
	 * @return
	 * @throws DataAccessException
	 */
	List<Event> getRecentEvents(String lastEventId, int limit, String compare) throws DataAccessException;

	/**
	 * @param timeInterval
	 * @return
	 * @throws DataAccessException
	 */
	List<EventCount> getTopEvents(int timeInterval) throws DataAccessException;

	/**
	 * @param timeInterVal
	 * @return
	 * @throws DataAccessException
	 */
	List<EventCount> getEventCount(int timeInterVal) throws DataAccessException;
}
