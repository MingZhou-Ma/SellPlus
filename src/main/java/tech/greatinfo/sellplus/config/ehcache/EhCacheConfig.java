/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月14日 下午6:01:07</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.config.ehcache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**     
* @Package：tech.greatinfo.sellplus.config.ehcache   
* @ClassName：CacheConfig   
* @Description：   <p> CacheConfig </p>
* @Author： - Jason   
* @CreatTime：2018年8月14日 下午6:01:07   
* @Modify By：   
* @ModifyTime：  2018年8月14日
* @Modify marker：   
* @version    V1.0
*/
@Configuration
@ConditionalOnProperty(name = "enabled", havingValue = "true",prefix="ehcache",matchIfMissing=false) 
public class EhCacheConfig {
	
	
	/**
	 * @Description: ehcache 主要的管理器
	 * @param bean
	 * @return EhCacheCacheManager
	 * @Autor: Jason
	 */
    @Bean(name = "appEhCacheCacheManager")
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
        return new EhCacheCacheManager (bean.getObject ());
    }

    /**
     * @Description: Spring分别通过CacheManager.create()或new CacheManager()方式来创建一个ehcache基地
     * @return EhCacheManagerFactoryBean
     * @Autor: Jason
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource ("ehcache/ehcache.xml"));
        cacheManagerFactoryBean.setShared (true);
        return cacheManagerFactoryBean;
    }
	
}
