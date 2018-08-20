/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.common.cache.redis;

/**
* @Package：tech.greatinfo.sellplus.common.cache.redis   
* @ClassName：RedisConstant   
* @Description：   <p> redis常量</p>
* @Author： - Jason   
* @CreatTime：2018年8月20日 下午3:39:26   
* @Modify By：   
* @ModifyTime：  2018年8月20日
* @Modify marker：   
* @version    V1.0
 */
public class RedisConstant {
	
	//-----------------------Redis常量信息-----------------------------
	
	/**
	 * redis 缓存key不存在返回的标志
	 */
	public static final String CACHE_KEY_NOT_EXIST = "nil";
	
	/**
	 * 缓存key的分割符
	 */
	public static final String CACHE_KEY_SPLIT_CHAR = ":";

	/**
	 * key模糊匹配通配符
	 */
	public static final String CACHE_KEY_PATTERN_CHAR = "*";
	
	
	//------------------------Redis常量信息 end-----------------------------
	
	
	
	//-----------------------RedisBiz常量信息 Start-----------------------------
	
	/**
	 * 优惠券key
	 */
	public static final String REDIS_BIZ_COUPONS_KEY = "coupons_key";
	
	
	
	
	
	
	
	
	
	
	//-----------------------RedisBiz常量信息 Start-----------------------------
	
}
