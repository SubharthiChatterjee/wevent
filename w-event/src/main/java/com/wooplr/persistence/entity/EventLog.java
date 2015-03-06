package com.wooplr.persistence.entity;

import java.util.Date;

import com.wooplr.persistence.common.MongoEntity;

/**
 * @author subharthi chatterjee
 * 
 */
public class EventLog implements MongoEntity<String> {

	private String eventId;
	private int type;
	private int timeInterval;
	private Date createDate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wooplr.persistence.common.MongoEntity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(String id) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.common.MongoEntity#getId()
	 */
	@Override
	public String getId() {
		return eventId + "_" + timeInterval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wooplr.persistence.common.MongoEntity#setCreateDate(java.util.Date)
	 */
	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.common.MongoEntity#getCreateDate()
	 */
	@Override
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

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
	 * @return the timeInterval
	 */
	public int getTimeInterval() {
		return timeInterval;
	}

	/**
	 * @param timeInterval
	 *            the timeInterval to set
	 */
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	/**
	 * @return the expireTime
	 */
	public Date getExpireTime() {
		return new Date(createDate.getTime() + (timeInterval * 60 * 60 * 1000));
	}

}
