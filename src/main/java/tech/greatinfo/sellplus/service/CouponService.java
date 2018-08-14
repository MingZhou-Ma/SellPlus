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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
	 * @Description: 添加优惠券
	 * @param coupon void
	 * @return 
	 * @Autor: Jason
	 */
    void insertCoupon(Coupon coupon);
    
    /**
     * @Description: 删除优惠券
     * @param id 
     * @return 
     * @Autor: Jason
     */
    void deleteCoupon(String id);
    
    
    /**
     * @Description:  更新优惠券
     * @param coupon
     * @return 
     * @Autor: Jason
     */
    void updateCoupon(Coupon coupon);
    
    
    /**
     * @Description: 获取优惠券
     * @param id
     * @return Coupon
     * @Autor: Jason
     */
    Coupon getCoupon(String id);
    
	
	/**
	 * @Description: findAll 
	 * @return List<Coupon>
	 * @Autor: Jason
	 */
	public List<Coupon> findAll();
	
	
	/**
	 * @Description: 分页查询优惠券
	 * @param pageable
	 * @return Page<Coupon>
	 * @Autor: Jason
	 */
	public Page<Coupon> findByPage(Pageable pageable);
	
	
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
