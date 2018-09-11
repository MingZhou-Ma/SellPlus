package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.repository.CouponsRepository;

/**
 *
 * 老司机相关事务
 *
 * Created by Ericwyn on 18-9-11.
 */
@Service
public class FreqService {
    @Autowired
    CouponsObjService objService;

    @Autowired
    CouponsRepository mCouponsRepository;


    // 老司机兑换自己的老司机优惠卷
    // 作为事务处理
    @Modifying
    @Transactional
    public void generalFreqConpons(int convertNum, Customer owner, Coupon coupon){
        List<CouponsObj> saveList = new ArrayList<>();
        for (int i=0;i<convertNum;i++){
            CouponsObj couponsObj = new CouponsObj();
            couponsObj.setOwn(owner);
            couponsObj.setCode(objService.getRandomCouponCode());
            couponsObj.setExpired(false);
            couponsObj.setCoupon(coupon);
            saveList.add(couponsObj);
        }
        objService.save(saveList);
    }

}
