package git.volkov.kvstorage.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Hash {

	private final static Logger LOG = LoggerFactory.getLogger(Md5Hash.class);

	private final MessageDigest digest;

	public Md5Hash() {
		try {
			digest = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Ooops... What's wrong?", e);
			throw new RuntimeException(e);
		}
	}

	public String getHash(String string) {
		digest.reset();
		digest.update(string.getBytes());
		return byteArray2Hex(digest.digest());

	}

	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

}
