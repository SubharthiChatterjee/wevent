package com.wooplr.persistence.common;

import org.apache.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * @author subharthi chatterjee Creates mongo database connection - a thread
 *         safe singleton though not 100% thread safe ;)
 */
public class MongoConnectionService {

	private Logger logger = Logger.getLogger(this.getClass());

	// constants for mongo options
	public final static String mongoServerUrl = "localhost:27017";

	public final static String database = "wooplr";

	private static MongoConnectionService service;

	private static Object lock = new Object();

	private MongoClient mongoClient;

	private DB db;

	private MongoConnectionService() {
		// initialize the service object
		initializeMongo();

	}

	private void initializeMongo() {
		try {
			mongoClient = new MongoClient(new ServerAddress("localhost:27017"));
			db = mongoClient.getDB(database);

		} catch (Exception e) {
			logger.error("Exception during mongo connection service initialization : ", e);
		}
	}

	/**
	 * @return the instance of mongo connection service
	 * 
	 */
	public static MongoConnectionService getInstance() {
		if (service == null) {
			synchronized (lock) {
				if (service == null) {
					service = new MongoConnectionService();
				}
				service.logger.debug("Mongo initialized!");
			}
		}
		return service;
	}

	public DB getDatabase() {
		return db;
	}
}
