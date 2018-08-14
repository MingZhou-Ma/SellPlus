/**
 * <html>
 * <body>
 *  <P> Copyright JasonInternational</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月14日 下午3:07:20</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.repository.CouponRepository;
import tech.greatinfo.sellplus.service.CouponService;

/**     
* @Package：tech.greatinfo.sellplus.service.impl   
* @ClassName：CouponServiceImpl   
* @Description：   <p> CouponServiceImpl </p>
* @Author： - Jason   
* @CreatTime：2018年8月14日 下午3:07:20   
* @Modify By：   
* @ModifyTime：  2018年8月14日
* @Modify marker：   
* @version    V1.0
*/
@Service
public class CouponServiceImpl implements CouponService{
	
	@Autowired
	CouponRepository couponRepository;
	
	@Override
	public List<Coupon> findAll() {
		return couponRepository.findAll();
	}

	@Override
	public void save(Coupon entity) {
		couponRepository.save(entity);
	}

	@Override
	public Coupon findById(String id) {
		return couponRepository.findOne(id);
	}

}
