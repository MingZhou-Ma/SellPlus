package tech.greatinfo.sellplus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Diary;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.repository.DiaryRepository;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by Ericwyn on 18-9-8.
 */
@Service
public class DiaryService {
    private static final Logger logger = LoggerFactory.getLogger(DiaryService.class);


    @Autowired
    DiaryRepository repository;

    @Autowired
    CompanyService companyService;

    @Autowired
    CouponsObjService objService;


    public Diary save(Diary diary) {
        return repository.save(diary);
    }

    public Diary findOne(Long diaryId) {
        return repository.findByDiaryId(diaryId);
    }

    public Diary findFirstByCustomerAndGeneralTrueOrderByGeneralTimeDesc(Customer customer) {
        return repository.findFirstByCustomerAndGeneralTrueOrderByGeneralTimeDesc(customer);
    }

    // 加注解代表事务
    @Transactional
    @Modifying
    public synchronized void generalCoupon(Long diaryId) {
        Diary diary = repository.findByDiaryId(diaryId);
        if (!diary.isGeneral() && diary.getReadHistory().split(",").length >= companyService.getDiaryReadNum()) {
            Coupon coupon = companyService.getDiaryCoupon();
            if (coupon == null) {
                logger.info("尚未设置心得分享的奖励优惠卷");
            } else {
                CouponsObj couponsObj = new CouponsObj();
                couponsObj.setCoupon(coupon);
                couponsObj.setCode(objService.getRandomCouponCode());
                couponsObj.setOwn(diary.getCustomer());
                couponsObj.setNote("心得分享奖励优惠卷");
                couponsObj.setGeneralTime(new Date());
                objService.save(couponsObj);
                diary.setGeneral(true);
                diary.setGeneralTime(new Date()); // 设置获取优惠券的时间
                repository.save(diary);
            }
        }
    }
}
