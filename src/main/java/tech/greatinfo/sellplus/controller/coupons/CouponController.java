/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月13日 下午5:32:20</p>
 *  <p> Created by WanGuo@Wanguo.com </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.controller.coupons;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tech.greatinfo.sellplus.common.cache.redis.RedisConstant;
import tech.greatinfo.sellplus.common.cache.redis.RedisLock;
import tech.greatinfo.sellplus.common.cache.redis.impl.RedisServiceImpl;
import tech.greatinfo.sellplus.common.vo.RespBody;
import tech.greatinfo.sellplus.config.ehcache.constants.EhcacheConstant;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.enums.CouponState;
import tech.greatinfo.sellplus.domain.coupons.enums.CouponType;
import tech.greatinfo.sellplus.service.CouponService;
import tech.greatinfo.sellplus.utils.cache.EhCacheUtil;
import tech.greatinfo.sellplus.utils.date.DateHelper;
import tech.greatinfo.sellplus.utils.obj.ResJson;
import tech.greatinfo.sellplus.utils.pk.PKGenerator;

/**     
* @Package：tech.greatinfo.sellplus.controller.coupons   
* @ClassName：CouponController   
* @Description：   <p> CouponController -  </p>
* @Author： - Jason   
* @CreatTime：2018年8月13日 下午5:33:11   
* @Modify By：   
* @ModifyTime：  2018年8月13日
* @Modify marker：   
* @version    V1.0
*/
@Api(tags="优惠券管理")
@RestController
//@RequestMapping(value="/api/v1/coupon",consumes=MediaType.APPLICATION_JSON_VALUE) //仅处理application/json请求、
@RequestMapping(value="/api/v1/coupon")
public class CouponController {
	
	private static final Logger logger = LoggerFactory.getLogger(CouponController.class);
	
	@Autowired
	protected CouponService couponService;

	@Autowired
	protected RedisServiceImpl redisService;
	
	//@Autowired
	//protected RedisLock redisLock;
	
	@Autowired 
	protected EhCacheCacheManager appEhCacheCacheManager;
	
	/**
	 * @Description:获取优惠券列表
	 * @return ResJson
	 * @Autor: Jason
	 */
	@ApiOperation(value = "获取优惠券列表", notes = "获取优惠券列表")
	@RequestMapping(value = "/list",method = RequestMethod.GET )
    public ResJson list(){
		List<Coupon> couponList = couponService.findAll();
		return ResJson.successJson("CouponController findAll", couponList);
    }
	 
	/**
	 * @Description: 根据id查询优惠券
	 * @param id
	 * @return ResJson
	 * @Autor: Jason
	 */
    @ApiOperation(value="根据id查询优惠券")  
	//@ApiImplicitParam(name="id",value="优惠券id",required=true)
    @RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
    public ResJson findCoupon(@PathVariable String id){
    	logger.info("优惠券的id为:{}",id);
    	Coupon coupon = couponService.findById(id);
    	return ResJson.successJson("CouponController findById", coupon);
    }
    
    /**
     * @Description: 根据id查询优惠券 
     * @param id
     * @return ResJson
     * @Autor: Jason
     */
    @ApiOperation(value="getCoupon根据id查询优惠券")  
	//@ApiImplicitParam(name="id",value="优惠券id",required=true)
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResJson getCoupon(@RequestParam(name = "id",required = true) String id){ //@RequestParam("id") @PathVariable("id")
    	logger.info("优惠券的id为:{}",id);
    	Coupon coupon = couponService.findById(id);
    	return ResJson.successJson("CouponController findById", coupon);
    }
    
    /**
     * @Description: https://blog.csdn.net/xupeng874395012/article/details/68946676
     * @param username
     * @return String
     * @Autor: Jason
     */
    @ApiOperation(value="RESTful Test")  
    //@ApiImplicitParam(name="username",value="测试的username",required=true)
    @RequestMapping(value = "/rest/{username}",method = RequestMethod.GET)
    public String RESTful(@PathVariable String username){
    	logger.info("api/rest:{}",username);
        return "Welcome,"+username;
    }
    
    
    @ApiOperation(value="PageHelper 分页查询")  
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public ResJson page(){
    	 Sort sort = new Sort(Sort.Direction.ASC,"id");
         Pageable pageable = new PageRequest(1,2,sort);//page  size
         Page<Coupon> pageList = couponService.findByPage(pageable);
         return ResJson.successJson("分页查询数据", pageList);
    }
    
    @ApiOperation(value="新增优惠券")  
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ResJson add(){
    	Coupon coupon = new Coupon();
    	coupon.setActName("wubin");
    	coupon.setActNo("123");//活动编码
    	coupon.setCouponState(CouponState.EXPIRE);
    	coupon.setCouponType(CouponType.COUPON);
    	couponService.insertCoupon(coupon);
         return ResJson.successJson("优惠券信息", coupon);
    }
    
    
    @ApiOperation(value="更新优惠券")  
    @RequestMapping(value = "/update/{name}/{remark}",method = RequestMethod.GET)
    public ResJson update(@PathVariable String name,@PathVariable String remark){
    	Coupon coupon = new Coupon();
    	coupon.setActName(name);
    	coupon.setRemark(remark);
    	coupon.setCouponState(CouponState.EXPIRE);
    	coupon.setCouponType(CouponType.COUPON);
    	couponService.insertCoupon(coupon);
         return ResJson.successJson("更新优惠券信息", coupon);
    }
    
    /**
     * @Description: ehcache缓存测试
     * @return RespBody
     * @Autor: Jason
     */
    @ApiOperation(value="ehcache缓存")  
    @RequestMapping(value = "/ehcache",method = RequestMethod.GET)
    public RespBody ehcache(){
    	RespBody respBody = new RespBody();
    	int limitCount = 10;
    	Cache viewCountCache = appEhCacheCacheManager.getCache(EhcacheConstant.EHCACHE_VIEW_COUNT);
		AtomicInteger viewCount = viewCountCache.get("ehcache_key", AtomicInteger.class);//CAS 线程安全考虑
		if (viewCount ==null) {
			viewCount = new AtomicInteger(0); 
			viewCountCache.put("ehcache_key", viewCount);
		}
		if (viewCount.incrementAndGet()>limitCount) {
			logger.info("超过最大允许访问次数{},当前已访问次数{}",limitCount,viewCount.get());
			respBody.addError("超过最大允许访问次数.");
		}else {
			logger.info("当前已访问次数{}",viewCount.get());
			respBody.addOK(viewCount.get(),"当前访问次数.");
		}
        return respBody;
    }
    
    /**
     * @Description: redis 缓存测试
     * @return RespBody
     * @Autor: Jason
     */
    @ApiOperation(value="redis缓存")  
    @RequestMapping(value = "/redis",method = RequestMethod.GET)
    public RespBody redis(){
    	RespBody respBody = new RespBody();
    	Coupon coupon = new Coupon();
    	coupon.setActName(PKGenerator.uuid32());
    	coupon.setActNo(PKGenerator.uuid32());
    	coupon.setCpCode(PKGenerator.uuid32());
    	coupon.setEndDate(new Date());
    	EhCacheUtil.put(EhcacheConstant.EHCACHE_VIEW_COUNT, coupon.getActNo(), JSON.toJSONString(coupon));
    	
    	logger.info("ehCache-k:{},v:{}",coupon.getActNo(),JSON.toJSONString(EhCacheUtil.get(EhcacheConstant.EHCACHE_VIEW_COUNT, coupon.getActNo())));
    	
    	redisService.hset(RedisConstant.REDIS_BIZ_COUPONS_KEY, coupon.getActNo(),JSON.toJSONString(coupon));
    	logger.info("Redis缓存数据,详细数据为:{}",JSON.toJSONString(redisService.hget(RedisConstant.REDIS_BIZ_COUPONS_KEY,coupon.getActNo())));
    	respBody.addOK(coupon,"存入缓存成功!");
        return respBody;
    }
    
    
    /**
     * @Description: Redis锁测试
     * @return RespBody
     * @throws InterruptedException 
     * @Autor: Jason
     */
    @ApiOperation(value="redis单机锁测试")  
    @RequestMapping(value = "/redisLock",method = RequestMethod.GET)
    public RespBody redisLock() throws InterruptedException{
    	RespBody respBody = new RespBody();
    	//String key, long timeout, int expire
    	Long timeout = 1000L;//获取锁的超时时间
    	Integer expire = 30; //锁的过期时间
    	
    /*	int threads = 100; //线程数
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
             executorService.execute(new Runnable() {
				@Override
				public void run() {
					String lockKey = DateHelper.getCurrentYMDHM();
					boolean lockFlag = false;
					RedisLock redisLock = new RedisLock(redisService.getRedisPool());
					try {
						do {
							lockFlag = redisLock.singleLock(lockKey, timeout, expire); //锁
							if (lockFlag) {
								logger.info("lockKey:{},event:分布式锁获取", lockKey);
								respBody.addOK(lockKey, "分布式锁获取");
							}
						} while (!lockFlag); //如果没有锁住的话 就一直去拿锁,锁住了就执行下面的业务
						logger.info("进行了业务处理业务处理!当前时间为:{}",DateHelper.getCurrentTime());
						respBody.addOK(new Date(), "进行了业务处理业务处理");
					} finally {
						redisLock.singleUnlock(lockKey);//无论加锁是否成功都需要解锁
						logger.info("lockKey:{},event:分布式锁释放", lockKey);
					}
				}
			});
        }*/
    	String lockKey = DateHelper.getCurrentYMDHM();
		boolean lockFlag = false;
		RedisLock redisLock = new RedisLock(redisService.getRedisPool());
		try {
			do {
				lockFlag = redisLock.singleLock(lockKey, timeout, expire); //锁key - 超时时间 -  过期时间
				if (lockFlag) {
					logger.info("lockKey:{},event:分布式锁获取", lockKey);
					respBody.addOK(lockKey, "分布式锁获取");
				}
			} while (!lockFlag); //如果没有锁住的话 就一直去拿锁,锁住了就执行下面的业务
			logger.info("进行了业务处理业务处理!当前时间为:{}",DateHelper.getCurrentTime());
			respBody.addOK(new Date(), "进行了业务处理业务处理");
		} finally {
			Thread.sleep(1000);//延时1000ms
			redisLock.singleUnlock(lockKey);//无论加锁是否成功都需要解锁
			logger.info("lockKey:{},event:分布式锁释放", lockKey);
		}
		return respBody;
    }

}
