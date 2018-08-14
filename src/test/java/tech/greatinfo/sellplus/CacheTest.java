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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

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
	
	@Autowired
	EhCacheManagerFactoryBean ehCacheManagerFactoryBean;
	
	@Test
    public void test() {
		logger.info("测试:EhCacheBean:{}",JSON.toJSONString(ehCacheManagerFactoryBean));
    }

}
