/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月13日 下午6:25:05</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.repository.CouponRepository;

/**     
* @Package：tech.greatinfo.sellplus.service   
* @ClassName：CouponService   
* @Description：   <p> CouponService </p>
* @Author： - Jason   
* @CreatTime：2018年8月13日 下午6:25:05   
* @Modify By：   
* @ModifyTime：  2018年8月13日
* @Modify marker：   
* @version    V1.0
*/
@Service
public class CouponService {
	
	@Autowired
	CouponRepository couponRepository;
	
	/**
	 * @Description: findAll 
	 * @return List<Coupon>
	 * @Autor: Jason
	 */
	public List<Coupon> findAll(){
		List<Coupon> coupons = couponRepository.findAll();
		return coupons;
    }
	
	/**
	 * @Description: save 
	 * @param coupon 
	 * @Autor: Jason
	 */
	public void save(Coupon coupon) {
		couponRepository.save(coupon);
	}

}
