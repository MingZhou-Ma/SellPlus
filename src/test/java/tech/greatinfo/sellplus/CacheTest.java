/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月14日 下午6:07:52</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import tech.greatinfo.sellplus.common.cache.redis.impl.RedisServiceImpl;
import tech.greatinfo.sellplus.config.ehcache.constants.EhcacheConstant;
import tech.greatinfo.sellplus.utils.pk.PKGenerator;

/**     
* @Package：tech.greatinfo.sellplus   
* @ClassName：CacheTest   
* @Description：   <p> CacheTest </p>
* @Author： - Jason   
* @CreatTime：2018年8月14日 下午6:07:52   
* @Modify By：   
* @ModifyTime：  2018年8月14日
* @Modify marker：   
* @version    V1.0
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);
	
	private static final String EHCACHE_KEY = "key";

	@Autowired 
	EhCacheCacheManager appEhCacheCacheManager;
	
	@Autowired
	RedisServiceImpl redisService;
	
	
	/*@Test
    public void testCache() {
		Cache cache = appEhCacheCacheManager.getCache(EHCACHE_NAME);
		cache.put(EHCACHE_KEY, 100);
		Integer nums = cache.get(EHCACHE_KEY, Integer.class);
		logger.info("testCache{}",nums);
    }*/
	
	
	@Test
    public void testCount() {
		
		Cache viewCountCache = appEhCacheCacheManager.getCache(EhcacheConstant.EHCACHE_VIEW_COUNT);
		
		AtomicInteger viewCount = viewCountCache.get(EHCACHE_KEY, AtomicInteger.class);//CAS 线程安全考虑
		
		if (viewCount ==null) {
			viewCount = new AtomicInteger(0);
			viewCountCache.put(EHCACHE_KEY, viewCount);
		}
		
		if (viewCount.incrementAndGet()>5) {
			logger.info("超过5次了,被限制!");
		}
		logger.info("当前{}被访问次数为{}","key",viewCount.incrementAndGet());
    }
	
	
	
	@Test
    public void testRedis() {
		redisService.hset("key", "test",PKGenerator.uuid32());
		logger.info("redis 存储成功!");
		String value = redisService.hget("key", "test");
		logger.info("redis取出成功!value值为:",value);
	}
		
			
	
}
