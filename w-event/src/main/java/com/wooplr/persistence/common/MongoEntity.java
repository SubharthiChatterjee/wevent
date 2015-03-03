package com.wooplr.persistence.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @author subharthi chatterjee
 * 
 */
public interface MongoEntity<T extends Serializable> {

	void setId(T id);

	T getId();

	void setCreateDate(Date createDate);

	Date getCreateDate();
}
