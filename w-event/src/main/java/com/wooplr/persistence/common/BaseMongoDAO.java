package com.wooplr.persistence.common;

import java.io.Serializable;
import java.util.List;

import com.mongodb.MongoException;

/**
 * @author subharthi chatterjee
 * 
 */
public interface BaseMongoDAO<E extends MongoEntity<T>, T extends Serializable> {

	String ID = "_id";
	String CREATE_DATE = "create_date";
	String UPDATE_DATE = "update_date";

	E get(T id) throws MongoException;

	E insert(E entity) throws MongoException;

	List<E> upsert(List<E> entity) throws MongoException;
}
