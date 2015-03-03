package com.wooplr.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.wooplr.commons.DataAccessException;
import com.wooplr.persistence.entity.Event;

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
	 * @param timeIntervalInMillis
	 * @return
	 * @throws DataAccessException
	 */
	List<Entry<Integer, Integer>> getTopEvents(long timeIntervalInMillis) throws DataAccessException;

	/**
	 * @param timeIntervalInMillis
	 * @return
	 */
	Map<Integer, Integer> getEventCount(long timeIntervalInMillis) throws DataAccessException;
}
