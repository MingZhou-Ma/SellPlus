/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月14日 下午6:10:11</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.common.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**     
* @Package：tech.greatinfo.sellplus.common.cache   
* @ClassName：EhCacheRepository   
* @Description：   <p> EhCacheRepository </p>
* @Author： - Jason   
* @CreatTime：2018年8月14日 下午6:10:11   
* @Modify By：   
* @ModifyTime：  2018年8月14日
* @Modify marker：   
* @version    V1.0
*/
@Component
@CacheConfig(cacheNames = "apiCallNumsCache")
public class EhCacheRepository {
	
	@Cacheable(key = "'test'+#test")
    public void findByCode(String code) {
        System.out.println("---> Loading country with code '" + code + "'");
    }

}
