/**
 * Copyright (c) 2011 by InfoArmy Inc.  All Rights Reserved.
 * This file contains proprietary information of InfoArmy Inc.
 * Copying, use, reverse engineering, modification or reproduction of
 * this file without prior written approval is prohibited.
 *
 */
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
