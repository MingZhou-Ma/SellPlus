package tech.greatinfo.sellplus;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.enums.CouponState;
import tech.greatinfo.sellplus.domain.coupons.enums.CouponType;
import tech.greatinfo.sellplus.service.CouponService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellPlusApplicationTests {
	
	private static final Logger logger = LoggerFactory.getLogger(SellPlusApplicationTests.class);

    @Autowired 
    CouponService couponService;
    
   /* @Test
    public void contextLoads() {
    }*/
    
    @Test
    public void test() {
    	List<Coupon> couponList = couponService.findAll();
    	System.out.println(JSON.toJSONString(couponList));
    }
    
   /* @Test
    public void saveCoupon() {
    	Coupon coupon = new Coupon();
    	coupon.setCouponType(CouponType.COUPON);
    	couponService.save(coupon);
    }*/
    
    @Test
    public void saveCoupon2() {
    	Coupon coupon = new Coupon();
    	coupon.setCouponState(CouponState.FRESH);//4
    	coupon.setCouponType(CouponType.OLDER_DIVER);//2
    	System.out.println("coupon SYSO:"+JSON.toJSONString(coupon));
    	logger.info("coupon:{}",JSON.toJSONString(coupon));
    	couponService.save(coupon);
    }

}
