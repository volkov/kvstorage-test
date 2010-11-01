package git.volkov.kvstorage;

public interface Storage {
	
	void init() throws Exception;
	
	void put(String key, String value);
	
	String get(String key);
}
