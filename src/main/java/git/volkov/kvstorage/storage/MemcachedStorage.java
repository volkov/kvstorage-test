package git.volkov.kvstorage.storage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import git.volkov.kvstorage.Storage;
import git.volkov.kvstorage.utils.Md5Hash;

/**
 * Storage interface for memcached database.
 * 
 * @author Sergey Volkov
 * 
 */
public class MemcachedStorage implements Storage {

	private static final int TIMEOUT = 10000;

	/**
	 * Host with memcached database.
	 */
	private String host;

	/**
	 * Memcached client.
	 */
	protected MemcachedClient client;
	
	/**
	 * We have to calculate hash because we are limited to 250 bytes key.
	 */
	private Md5Hash md5Hash = new Md5Hash();

	@Override
	public void init() throws Exception {
		MemcachedClientBuilder memcachedClientBuilder = new XMemcachedClientBuilder(
				AddrUtil.getAddresses(host));
		client = memcachedClientBuilder.build();
	}

	@Override
	public void put(String key) throws Exception {
		client.set(md5Hash.getHash(key), 0, "1",TIMEOUT);
	}

	@Override
	public boolean has(String key) throws Exception {
		return (client.get(md5Hash.getHash(key),TIMEOUT) != null);
	}

	@Override
	public void clean() throws Exception {
		client.shutdown();
	}

	/**
	 * @param host
	 *            the host to set
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
	
	public String toString(){
		return "Memcached";
	}
}
