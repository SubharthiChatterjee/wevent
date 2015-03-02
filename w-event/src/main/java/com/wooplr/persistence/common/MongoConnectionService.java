package com.wooplr.persistence.common;

import org.apache.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.MongoURI;

/**
 * @author subharthi chatterjee Creates mongo database connection - a thread
 *         safe singleton though not 100% thread safe ;)
 */
public class MongoConnectionService {

	private Logger logger = Logger.getLogger(this.getClass());

	// constants for mongo options

	private static MongoConnectionService service;

	private static Object lock = new Object();
	private DB db;

	private MongoConnectionService() {
		// initialize the service object
		initializeMongo();

	}

	private void initializeMongo() {
		try {

			MongoURI mongoURI = new MongoURI("mongodb://sub123:sub123@ds049651.mongolab.com:49651/wooplr");
			db = mongoURI.connectDB();

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
