package git.volkov.kvstorage.storage;

import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.transcoders.TokyoTyrantTranscoder;
import net.rubyeye.xmemcached.utils.AddrUtil;

/**
 * Interface for TokyoTyrant through xmemcached api.
 * @author Sergey Volkov
 *
 */
public class TokyoStorage extends MemcachedStorage{
	
	/**
	 * Inits xmemcached client to work with tokyo tyrant.
	 */
	@Override
	public void init() throws Exception{
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(
				AddrUtil.getAddresses(getHost()));
		builder.setTranscoder(new TokyoTyrantTranscoder());
		client = builder.build();
	}
	
	@Override
	public String toString(){
		return "Tokyo Tyrant/Cabinet";
	}
	
}
