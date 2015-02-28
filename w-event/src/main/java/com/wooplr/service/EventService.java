/**
 * Copyright (c) 2011 by InfoArmy Inc.  All Rights Reserved.
 * This file contains proprietary information of InfoArmy Inc.
 * Copying, use, reverse engineering, modification or reproduction of
 * this file without prior written approval is prohibited.
 *
 */
package com.wooplr.service;

import java.util.List;
import java.util.Map;

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
	List<Event> getRecentEvents(String lastEventId, int limit) throws DataAccessException;

	List<Event> getTopEvents(String lastEventId, int limit, long timeIntervalInMillis) throws DataAccessException;

	/**
	 * @param timeIntervalInMillis
	 * @return
	 */
	Map<Integer, Integer> getEventCount(long timeIntervalInMillis) throws DataAccessException;
}
