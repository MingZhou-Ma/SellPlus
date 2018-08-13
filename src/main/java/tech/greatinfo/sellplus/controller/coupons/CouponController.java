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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
public class CouponController {
	
	@Autowired
	CouponService couponService;
	
	@RequestMapping(value = "/api/coupon/list",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public ResJson list(){
		List<Coupon> couponList = couponService.findAll();
		return ResJson.successJson("CouponController findAll", couponList);
    }

}
