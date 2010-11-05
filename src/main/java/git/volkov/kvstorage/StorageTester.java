package git.volkov.kvstorage;

import git.volkov.kvstorage.utils.FileFeeder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for testing storages.
 * 
 * @author Sergey Volkov
 * 
 */
public class StorageTester {

	private static class TestThread extends Thread {

		protected static final Logger LOG = LoggerFactory
				.getLogger(PutThread.class);

		/**
		 * Storage that is used by thread
		 */
		protected Storage storage;

		/**
		 * Feeder for thread
		 */
		protected FileFeeder feeder;

		/**
		 * Creates storage with current storage and feeder.
		 * 
		 * @param storage
		 */
		public TestThread(Storage storage, FileFeeder feeder) {
			this.storage = storage;
			this.feeder = feeder;
		}
	}

	private static class PutThread extends TestThread {

		public PutThread(Storage storage, FileFeeder feeder) {
			super(storage, feeder);
		}

		@Override
		public void run() {
			LOG.info("Start thread " + getName());
			String key;
			try {
				while ((key = feeder.getLine()) != null) {
					storage.put(key);
				}
			} catch (Exception e) {
				LOG.error("Some error in thread " + this.getName(), e);
				throw new RuntimeException();
			}
		}
	}

	private class GetThread extends TestThread {
		
		private int miss = 0;
		
		public GetThread(Storage storage, FileFeeder feeder) {
			super(storage, feeder);
		}

		@Override
		public void run() {
			LOG.info("Start thread " + getName());
			String key;
			try {
				while ((key = feeder.getLine()) != null) {
					if (!storage.has(key)) miss++;
				}
			} catch (Exception e) {
				LOG.error("Some error in thread " + this.getName(), e);
				throw new RuntimeException();
			}
		}
		
		public int getMiss(){
			return miss;
		}

	}

	private static final Logger LOG = LoggerFactory
			.getLogger(StorageTester.class);

	/**
	 * List of storage to test.
	 */
	private List<Storage> storages = new ArrayList<Storage>();

	/**
	 * Input file name for keys to put in database.
	 */
	private String putFile = "put";

	/**
	 * Input file name for keys to test get.
	 */
	private String getFile = "get";
	
	/**
	 * Number of threads for tests.
	 */
	private int threadNumber = 0;
	
	/**
	 * Runs put's.
	 * 
	 * @param storage
	 * @throws IOException
	 */
	private void runPut(Storage storage) throws Exception {
		LOG.info("Testing put for storage " + storage.toString());
		FileFeeder feeder = new FileFeeder(putFile);
		LOG.info("Create " + threadNumber + " threads");
		List<PutThread> threads = new ArrayList<StorageTester.PutThread>();
		for (int i = 0; i < threadNumber; i++) {
			PutThread thread = new PutThread(storage, feeder);
			threads.add(thread);
		}

		LOG.info("Start threads...");
		long start = System.currentTimeMillis();
		for (PutThread thread:threads){
			thread.start();
		}
		for (PutThread thread:threads){
			thread.join();
		}
		
		long time = System.currentTimeMillis() - start;
		LOG.info(String.format("Finished %d put for %d ms", feeder.getLines(), time));
	}

	private void runGet(Storage storage) throws Exception {
		LOG.info("Testing get for storage " + storage.toString());
		FileFeeder feeder = new FileFeeder(getFile);
		LOG.info("Create " + threadNumber + " threads");
		List<GetThread> threads = new ArrayList<StorageTester.GetThread>();
		for (int i = 0; i < threadNumber; i++) {
			GetThread thread = new GetThread(storage, feeder);
			threads.add(thread);
		}
		
		LOG.info("Start threads...");
		long start = System.currentTimeMillis();
		for (GetThread thread:threads){
			thread.start();
		}
		for (GetThread thread:threads){
			thread.join();
		}
		long time = System.currentTimeMillis() - start;

		int miss = 0;
		for (GetThread thread : threads) {
			miss+=thread.getMiss();
		}
		
		LOG.info(String.format("Finished %d get with %d miss for %d ms", feeder.getLines(),
				miss, time));
	}

	public void runTest() {
		for (Storage storage : storages) {
			try {
				storage.init();
				runPut(storage);
				runGet(storage);
				storage.clean();
			} catch (Exception e) {
				LOG.error("Some problem occured while testing storage "
						+ storage, e);
			}

		}
	}

	public List<Storage> getStorages() {
		return storages;
	}

	public void setStorages(List<Storage> storages) {
		this.storages = storages;
	}

	public String getPutFile() {
		return putFile;
	}

	public void setPutFile(String putFile) {
		this.putFile = putFile;
	}

	public String getGetFile() {
		return getFile;
	}

	public void setGetFile(String getFile) {
		this.getFile = getFile;
	}

	/**
	 * @param threadNumber the threadNumber to set
	 */
	public void setThreadNumber(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	/**
	 * @return the threadNumber
	 */
	public int getThreadNumber() {
		return threadNumber;
	}

}
