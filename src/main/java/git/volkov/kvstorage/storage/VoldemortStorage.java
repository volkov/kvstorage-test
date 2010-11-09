package git.volkov.kvstorage.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;
import git.volkov.kvstorage.Storage;

/**
 * Implements Voldemort storage.
 * @author Sergey Volkov
 *
 */
public class VoldemortStorage implements Storage{

	private static final Logger LOG = LoggerFactory.getLogger(VoldemortStorage.class);
	
	private String host;
	
	private StoreClient<String, String> client;
	
	@Override
	public void init() throws Exception {
		LOG.info("Voldemort storage for "+ host);
		StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(getHost()));
		client = factory.getStoreClient("test");
	}

	@Override
	public void put(String key) throws Exception {
		client.put(key, "1");
	}

	@Override
	public boolean has(String key) throws Exception {
		return client.get(key)!=null;
	}

	@Override
	public void clean() throws Exception {
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
	
	@Override
	public String toString(){
		return "Voldemort";
	}

}
