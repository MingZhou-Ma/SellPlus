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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
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


}
