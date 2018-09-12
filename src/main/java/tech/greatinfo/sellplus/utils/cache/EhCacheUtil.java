/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月20日 下午4:45:40 </p>
 *  <p> Created by WanGuo@Wanguo.com </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.utils.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
* @Package：tech.greatinfo.sellplus.utils.cache   
* @ClassName：EhCacheUtil   
* @Description：   <p> Ehcache工具类</p>
* @Author： - Jason   
* @CreatTime：2018年8月20日 下午4:36:18   
* @Modify By：   
* @ModifyTime：  2018年8月20日
* @Modify marker：   
* @version    V1.0
 */
public class EhCacheUtil {

	/**
	 * @Description: 获取缓存
	 * @param cacheName 缓存名字
	 * @return Cache
	 * @Autor: Jason
	 */
    private static Cache getCache(String cacheName) {
        CacheManager cacheManager = CacheManager.getInstance();
        if (null == cacheManager) {
            return null;
        }
        Cache cache = cacheManager.getCache(cacheName);
        if (null == cache) {
            return null;
        }
        return cache;
    }

    /**
     * @Description:    新增缓存记录
     * @param cacheName 缓存名字
     * @param key       缓存key
     * @param value 
     * @Autor: Jason
     */
    public static void put(String cacheName, String key, Object value) {
        Cache cache = getCache(cacheName);
        if (null != cache) {
            Element element = new Element(key, value);
            cache.put(element);
        }
    }

    /**
     * @Description: 删除缓存记录 
     * @param cacheName 缓存名字
     * @param key       缓存key
     * @return boolean
     * @Autor: Jason
     */
    public static boolean remove(String cacheName, String key) {
        Cache cache = getCache(cacheName);
        if (null == cache) {
            return false;
        }
        return cache.remove(key);
    }

    /**
     * @Description: 删除全部缓存记录
     * @param cacheName 
     * @Autor: Jason
     */
    public static void removeAll(String cacheName) {
        Cache cache = getCache(cacheName);
        if (null != cache) {
            cache.removeAll();//logOnRemoveAllIfPinnedCache();
        }
    }

    /**
     * @Description: 获取缓存记录
     * @param cacheName  缓存名字
     * @param key        缓存key
     * @return Object
     * @Autor: Jason
     */
    public static Object get(String cacheName, String key) {
        Cache cache = getCache(cacheName);
        if (null == cache) {
            return null;
        }
        Element cacheElement = cache.get(key);
        if (null == cacheElement) {
            return null;
        }
        return cacheElement.getObjectValue();
    }


}
