package git.volkov.kvstorage;

import git.volkov.kvstorage.utils.Md5Hash;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class Test {
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException, MemcachedException, NoSuchAlgorithmException {
		Md5Hash hash = new Md5Hash();
		System.out.println(hash.getHash("test"));
		System.out.println(hash.getHash("test1"));
	}
}
