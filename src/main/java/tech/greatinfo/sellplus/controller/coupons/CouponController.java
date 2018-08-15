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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tech.greatinfo.sellplus.common.vo.RespBody;
import tech.greatinfo.sellplus.config.ehcache.constants.EhcacheConstant;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.enums.CouponState;
import tech.greatinfo.sellplus.domain.coupons.enums.CouponType;
import tech.greatinfo.sellplus.service.CouponService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

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
//@RequestMapping(value="/api/v1/coupon",consumes=MediaType.APPLICATION_JSON_VALUE) //仅处理application/json请求
@RequestMapping(value="/api/v1/coupon")
public class CouponController {
	
	private static final Logger logger = LoggerFactory.getLogger(CouponController.class);
	
	@Autowired
	CouponService couponService;
	
	@Autowired 
	EhCacheCacheManager appEhCacheCacheManager;
	
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
    


}
