package com.wooplr.persistence.entity;

import java.util.Date;

import com.wooplr.persistence.common.MongoEntity;

/**
 * @author subharthi chatterjee
 * 
 */
public class EventCount implements MongoEntity<String> {

	private int type;
	private int count;
	private int timeIntervalInHours;
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
		return type + "_" + timeIntervalInHours;
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

	/**
	 * @return the timeIntervalInHours
	 */
	public int getTimeIntervalInHours() {
		return timeIntervalInHours;
	}

	/**
	 * @param timeIntervalInHours
	 *            the timeIntervalInHours to set
	 */
	public void setTimeIntervalInHours(int timeIntervalInHours) {
		this.timeIntervalInHours = timeIntervalInHours;
	}

}
