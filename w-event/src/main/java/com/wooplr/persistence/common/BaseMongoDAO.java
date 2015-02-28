/**
 * Copyright (c) 2011 by InfoArmy Inc.  All Rights Reserved.
 * This file contains proprietary information of InfoArmy Inc.
 * Copying, use, reverse engineering, modification or reproduction of
 * this file without prior written approval is prohibited.
 *
 */
package com.wooplr.persistence.common;

import java.io.Serializable;

import com.mongodb.MongoException;

/**
 * @author subharthi chatterjee
 * 
 */
public interface BaseMongoDAO<E extends MongoEntity<T>, T extends Serializable> {

	String ID = "_id";
	String CREATE_DATE = "create_date";

	E get(T id) throws MongoException;

	E insert(E entity) throws MongoException;
}
