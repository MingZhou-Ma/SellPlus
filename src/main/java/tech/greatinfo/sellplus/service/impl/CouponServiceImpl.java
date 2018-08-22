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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import tech.greatinfo.sellplus.common.exception.SystemException;
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
	
	private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
	
	@Autowired
	CouponRepository couponRepository;
	
	//@Autowired
	//RedisService redisService;
	
	
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


	@Override
	public Coupon getCoupon(String id) {
		return null;
	}

	@Override
	public void insertCoupon(Coupon coupon) {
		try {
			Assert.notNull(coupon,"coupon不允许为空!");
			couponRepository.save(coupon);//存入的时候存一份缓存
			//redisService.hset("key", "a", "good");
		} catch (Exception e) {
			logger.error("插入优惠券异常:{}", e);
			throw new SystemException(e);
		}
	}

	@Override
	public void deleteCoupon(String id) {
		try {
			Assert.notNull(id,"id不允许为空!");
			couponRepository.delete(id);
		} catch (Exception e) {
			logger.error("删除优惠券异常:{}", e);
			throw new SystemException(e);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		try {
			Assert.notNull(coupon,"coupon不允许为Null!");
			couponRepository.saveAndFlush(coupon);
		} catch (Exception e) {
			logger.error("更新优惠券异常:{}", e);
			throw new SystemException(e);
		}
	}

	@Override
	public Page<Coupon> findCouponByPage(Pageable pageable) {
		try {
			Assert.notNull(pageable,"pageable不允许为Null!");
			return couponRepository.findAll(pageable);
		} catch (Exception e) {
			logger.error("分页查询优惠券异常:{}", e);
			throw new SystemException(e);
		}
	}

	@Override
	public List<Coupon> getCouponsBySearch(Coupon search) {
		try {
			Assert.notNull(search,"优惠券条件不允许为null");
			return couponRepository.findAll();
		} catch (Exception e) {
			logger.error("根据条件获取优惠券异常:{}", e);
			throw new SystemException(e);
		}
	}

}
