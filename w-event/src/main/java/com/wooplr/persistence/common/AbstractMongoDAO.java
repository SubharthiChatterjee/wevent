package com.wooplr.persistence.common;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * @author subharthi chatterjee
 * 
 */
public abstract class AbstractMongoDAO<T extends MongoEntity<I>, I extends Serializable> implements BaseMongoDAO<T, I> {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected final MongoConnectionService service = MongoConnectionService.getInstance();

	protected DB getDB() {
		return service.getDatabase();
	}

	protected abstract String getCollectionName();

	protected abstract T map(DBObject dbObject);

	protected abstract DBObject map(T entity);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wooplr.persistence.common.BaseMongoDAO#get(java.io.Serializable)
	 */
	@Override
	public T get(I id) throws MongoException {
		DBObject dbQuery = new BasicDBObject(ID, id);
		DBObject dbObject = getDB().getCollection(getCollectionName()).findOne(dbQuery);
		return map(dbObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wooplr.persistence.common.BaseMongoDAO#insert(com.wooplr.persistence
	 * .common.MongoEntity)
	 */
	@Override
	public T insert(T entity) throws MongoException {
		getDB().getCollection(getCollectionName()).insert(map(entity));
		return entity;
	}
}
