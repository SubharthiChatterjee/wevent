package com.wooplr.persistence.entity;

import java.util.Date;

import com.wooplr.persistence.common.MongoEntity;

/**
 * @author subharthi chatterjee
 * 
 */
public class Event implements MongoEntity<String> {

	private String id;
	private int type;
	private String value1;
	private String value2;
	private Date createDate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wooplr.persistence.common.MongoEntity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.common.MongoEntity#getId()
	 */
	@Override
	public String getId() {
		return id;
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
	 * @return the value1
	 */
	public String getValue1() {
		return value1;
	}

	/**
	 * @param value1
	 *            the value1 to set
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	/**
	 * @return the value2
	 */
	public String getValue2() {
		return value2;
	}

	/**
	 * @param value2
	 *            the value2 to set
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Event [id=").append(id).append(", type=").append(type).append(", value1=").append(value1)
				.append(", value2=").append(value2).append(", createDate=").append(createDate).append("]");
		return builder.toString();
	}

}
