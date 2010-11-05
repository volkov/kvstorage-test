package git.volkov.kvstorage.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class feeds lines from file.
 * 
 * @author Sergey Volkov
 * 
 */
public class FileFeeder {

	/**
	 * Standart logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(FileFeeder.class);

	private int lines;

	private boolean finished = false;

	private BufferedReader reader;
	
	/**
	 * Creates feeder for file.
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public FileFeeder(String fileName) throws FileNotFoundException {
		LOG.info("Creating reader for file "+ fileName);
		reader = new BufferedReader(new FileReader(fileName));
	}
	
	/**
	 * @return next file line or null if empty.
	 * @throws IOException
	 */
	public synchronized String getLine() throws IOException {
		if (finished) {
			return null;
		}
		String line = reader.readLine();
		if (line != null) {
			lines++;
		} else {
			LOG.info("File finished");
			finished = true;
			reader.close();
		}
		return line;
	}
	
	/**
	 * 
	 * @return number of read lines
	 */
	public int getLines() {
		return lines;
	}
}
