/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月13日 下午6:23:35</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.coupons.Coupon;

/**     
* @Package：tech.greatinfo.sellplus.repository   
* @ClassName：CouponRepository   
* @Description：   <p> CouponRepository </p>
* @Author： - Jason   
* @CreatTime：2018年8月13日 下午6:23:35   
* @Modify By：   
* @ModifyTime：  2018年8月13日
* @Modify marker：   
* @version    V1.0
*/
public interface CouponRepository  extends JpaRepository<Coupon, String> , JpaSpecificationExecutor<Coupon>{

}
