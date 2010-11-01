package git.volkov.kvstorage.mongo;

import java.io.IOException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import git.volkov.kvstorage.Storage;

/**
 * MongoDb storage.
 * 
 * @author Sergey Volkov
 * 
 * @param <K>
 * @param <V>
 */
public class MongoStorage implements Storage {
	/**
	 * Standart logger.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(MongoStorage.class);

	/**
	 * MongoDb connection
	 */
	private Mongo mongo;

	/**
	 * MongoDb collection - something like table in sql.
	 */
	private DBCollection collection;
	
	/**
	 * DB host.
	 */
	private String host;
	
	/**
	 * DB port.
	 */
	private int port;
	/**
	 * Connects to local db
	 */
	@Override
	public void init() throws Exception {
		mongo = new Mongo();
		DB db = mongo.getDB("test");
		collection = db.getCollection("test");
	}

	@Override
	public void put(String key, String value) {
		BasicDBObject doc = new BasicDBObject();
		doc.put("key", key);
		doc.put("value", value);
		collection.insert(doc);
	}

	@Override
	public String get(String key) {
		BasicDBObject query = new BasicDBObject();
		query.put("key", key);
		DBCursor cursor = collection.find(query);
		while (cursor.hasNext()) {
			return cursor.next().get("value").toString();
		}
		return null;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

}
