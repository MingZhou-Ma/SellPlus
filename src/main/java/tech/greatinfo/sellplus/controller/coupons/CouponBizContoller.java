/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> 2018年8月23日 下午12:00:14 </p>
 *  <p> Created by WanGuo@Wanguo.com </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.controller.coupons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tech.greatinfo.sellplus.common.cache.redis.RedisConstant;
import tech.greatinfo.sellplus.common.cache.redis.impl.RedisServiceImpl;
import tech.greatinfo.sellplus.common.vo.RespBody;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.enums.CouponType;
import tech.greatinfo.sellplus.service.CouponService;
import tech.greatinfo.sellplus.utils.math.RandomUtils;

/**     
* @Package：tech.greatinfo.sellplus.controller.coupons   
* @ClassName：CouponBizContoller - biz业务处理 
* @Description：   <p> 优惠券的校验 - 生成 - 指派 - 核销 - 过期处理等 - 接口详见Swagger-ui</p>
* @Author： - Jason   
* @CreatTime：2018年8月23日 下午12:00:14   
* @Modify By：   
* @ModifyTime：  2018年8月23日
* @Modify marker：   
* @version    V1.0
*/
@Api(tags="优惠券业务处理")
@RestController
@RequestMapping(value="/api/v1/couponManager")
public class CouponBizContoller {

	private static final Logger logger = LoggerFactory.getLogger(CouponBizContoller.class);
	
	@Autowired
	protected CouponService couponService;
	
	@Autowired
	protected RedisServiceImpl redisService;

	/**
	 * @Description: 优惠券的创建
	 * @param token
	 * @return RespBody
	 * @Autor: Jason
	 */
	@ApiOperation(value = "创建指定的优惠券", notes = "获取优惠券<br/> 创建指定类型的优惠券<br/>")
	@RequestMapping(value = "/makeCoupon",method = RequestMethod.POST )
    public RespBody makeCoupon(@RequestParam(name = "token") String token,@RequestParam(name = "couponType") Integer couponType){
		RespBody respBody = new RespBody();
		try {
			Coupon coupon = new Coupon();
			Long cpCodeFlag = 0l;
			//redisService.hset(RedisConstant.REDIS_BIZ_COUPONS_CODE_KEY, coupon.getCpCode(), JSON.toJSONString(coupon));
			do {
				coupon.setCpCode(RandomUtils.generateUpperString(6));//6位
				cpCodeFlag = redisService.sadd(RedisConstant.REDIS_BIZ_CP_CODE_SET_KEY, coupon.getCpCode()); 
			} while (cpCodeFlag == 0l); //1l 不存在的值
			
			coupon.setCouponType(CouponType.values()[couponType]); //现金券
			couponService.insertCoupon(coupon);
			respBody.addOK(coupon, "创建成功!");
		} catch (Exception e) {
			respBody.addException("创建指定优惠券异常", 10012);
			logger.error("创建指定优惠券异常,异常信息为:{}",e);
		}
		return respBody;
    }
	
	
	/**
	 * @Description: 优惠券的核销
	 * @param code    优惠券券码
	 * @return RespBody
	 * @Autor: Jason
	 */
	@ApiOperation(value = "优惠券核销", notes = "根据优惠券券码<br/><font color='red'>核销优惠券.</font>")
	@RequestMapping(value = "/verifyCoupon",method = RequestMethod.POST )
    public RespBody verifyCoupon(@RequestParam(name = "code") String code){
		RespBody respBody = new RespBody();
		try {
			respBody.addOK(null, "优惠券核销成功!");
			//int i = 1/0;
		} catch (Exception e) {
			respBody.addException("优惠券核销异常", 10010);
			logger.error("优惠券核销异常,异常信息为:{}",e);
		}
		return respBody;
    }
	
	
	/**
	 * @Description: 校验券码是否已经存在
	 * @param code
	 * @return RespBody
	 * @Autor: Jason
	 */
	@ApiOperation(value = "优惠券券码是否已经存在", notes = "根据优惠券券码<br/>校验优惠券券码是否已经存在.")
	@RequestMapping(value = "/checkCouponExsist",method = RequestMethod.POST )
    public RespBody checkCouponExsist(@RequestParam(name = "code") String code){
		RespBody respBody = new RespBody();
		try {
			Assert.notNull(code, "不允许为Null.");
			Boolean sismember = redisService.sismember(RedisConstant.REDIS_BIZ_CP_CODE_SET_KEY, code);
			if (sismember) {//已存在
				respBody.addOK(code, "优惠券券码已存在!");
			}else {
				respBody.addOK(code, "优惠券券码不存在!");
			}
		} catch (Exception e) {
			respBody.addException("优惠券核销异常", 10010);
			logger.error("优惠券核销异常,异常信息为:{}",e);
		}
		return respBody;
    }
	
	
	
}
