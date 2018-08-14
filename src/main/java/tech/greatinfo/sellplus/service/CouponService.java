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

import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.coupons.Coupon;

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
public interface CouponService {
	
	/**
	 * @Description: findAll 
	 * @return List<Coupon>
	 * @Autor: Jason
	 */
	public List<Coupon> findAll();
	
	/**
	 * @Description: save 
	 * @param coupon 
	 * @Autor: Jason
	 */
	public void save(Coupon entity);
	
	/**
	 * @Description: findById 
	 * @param id
	 * @return Coupon
	 * @Autor: Jason
	 */
	public Coupon findById(String id);
	

}
