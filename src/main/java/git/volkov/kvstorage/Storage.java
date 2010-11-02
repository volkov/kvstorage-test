package git.volkov.kvstorage;

public interface Storage {
	
	/**
	 * Init storage for usage.
	 * @throws Exception
	 */
	void init() throws Exception;
	
	/**
	 * Puts key in storage.
	 * @param key
	 */
	void put(String key);
	
	/**
	 * Check if key is in storage now.
	 * @param key
	 * @return
	 */
	boolean has(String key);
	
	/**
	 * Clean all storage data.
	 */
	void clean();
}
