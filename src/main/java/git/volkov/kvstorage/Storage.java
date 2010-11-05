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
	 * @throws Exception 
	 */
	void put(String key) throws Exception;
	
	/**
	 * Check if key is in storage now.
	 * @param key
	 * @return
	 */
	boolean has(String key) throws Exception;
	
	/**
	 * Clean all storage data.
	 * @throws Exception 
	 */
	void clean() throws Exception;
}
