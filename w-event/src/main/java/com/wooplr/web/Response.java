/**
 * Copyright (c) 2011 by InfoArmy Inc.  All Rights Reserved.
 * This file contains proprietary information of InfoArmy Inc.
 * Copying, use, reverse engineering, modification or reproduction of
 * this file without prior written approval is prohibited.
 *
 */
package com.wooplr.web;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author subharthi chatterjee
 * 
 */
public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	private String message;
	private int code;
	private ArrayList eventList;
	private String lastEventId;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the eventList
	 */
	public ArrayList getEventList() {
		return eventList;
	}

	/**
	 * @param eventList
	 *            the eventList to set
	 */
	public void setEventList(ArrayList eventList) {
		this.eventList = eventList;
	}

	/**
	 * @return the lastEventId
	 */
	public String getLastEventId() {
		return lastEventId;
	}

	/**
	 * @param lastEventId
	 *            the lastEventId to set
	 */
	public void setLastEventId(String lastEventId) {
		this.lastEventId = lastEventId;
	}
}
