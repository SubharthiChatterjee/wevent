package com.wooplr.web;

import java.io.Serializable;

/**
 * @author subharthi chatterjee
 * 
 */
public class EventCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private int count;

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EventCount [type=").append(type).append(", count=").append(count).append("]");
		return builder.toString();
	}
}
