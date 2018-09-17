package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.repository.CouponsHistoryRepository;
import tech.greatinfo.sellplus.repository.CouponsObjRepository;
import tech.greatinfo.sellplus.repository.CouponsRepository;

/**
 * Created by Ericwyn on 18-9-6.
 */
@Service
public class CouponsService {
    @Autowired
    CouponsRepository couponsRepository;

    @Autowired
    CouponsObjRepository objRepository;

    @Autowired
    CouponsHistoryRepository historyRepository;

    public Coupon save(Coupon coupons){
        return couponsRepository.save(coupons);
    }

    public Coupon findOne(Long id){
        return couponsRepository.findOne(id);
    }

    public Page<Coupon> findAll(int start, int num){
        return couponsRepository.findAll(new PageRequest(start,num));
    }

    @Transactional
    @Modifying
    public void delete(Coupon coupon){
        // TODO 最好改为级联删除
        historyRepository.deleteAllByCouponObj_Coupon(coupon);
        objRepository.deleteAllByCoupon(coupon);
        couponsRepository.delete(coupon);
    }
}
