package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Company;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.repository.CompanyRepository;
import tech.greatinfo.sellplus.repository.CouponsRepository;

import java.util.List;

/**
 *
 * Created by Ericwyn on 18-8-13.
 */
@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CouponsRepository couponsRepository;

    public void saveMainInfo(List<Company> list){
        companyRepository.save(list);
    }

    public Company findByKey(String key){
        return companyRepository.findByK(key);
    }

    public List<Company> findAll(){
        return companyRepository.findAll();
    }

    /**
     * 返回心得分享阅读数量阈值
     * @return
     */
    public int getDiaryReadNum(){
        Company company = companyRepository.findByK("diaryReadNum");
        if (company == null){
            return -1;
        }else {
            try {
                return Integer.parseInt(company.getV());
            }catch (Exception e){
                return -1;
            }
        }
    }

    /**
     * 返回心得分享奖励优惠卷
     * @return
     */
    public Coupon getDiaryCoupon(){
        Company company = companyRepository.findByK("diaryCoupon");
        if (company == null){
            return null;
        }else {
            try {
                Long couponid = Long.parseLong(company.getV());
                return couponsRepository.findOne(couponid);
            }catch (Exception e){
                return null;
            }
        }
    }

    /**
     * 返回心得分享获取优惠券的间隔时间（即获得一张券之后隔多久才能再次获取优惠券）
     * @return
     */
    public int getDiaryIntervals() {
        Company company = companyRepository.findByK("diaryIntervals");
        if (null == company) {
            return -1;
        }
        try {
            return Integer.parseInt(company.getV());
        }catch (Exception e){
            return -1;
        }
    }

    public Coupon getFreqCoupon(){
        Company company = companyRepository.findByK("freqCouponId");
        if (null == company){
            return null;
        }
        try {
            Long couponId = Long.parseLong(company.getV());
            return couponsRepository.findOne(couponId);
        }catch (Exception e){
            return null;
        }
    }

    public double getFreqBonus(){
        Company company = companyRepository.findByK("freqBonus");
        if (null == company){
            return -1;
        }
        try {
            return Double.parseDouble(company.getV());
        }catch (Exception e){
            return -1;
        }
    }

    public Boolean getIsOpenLottery() {
        Company company = companyRepository.findByK("isOpenLottery");
        if (null == company) {
            return false;
        }
        try {
            return Boolean.parseBoolean(company.getV());
        }catch (Exception e){
            return false;
        }
    }

    public String getLotteryStr() {
        Company company = companyRepository.findByK("lotteryStr");
        if (null == company) {
            return null;
        }
        return company.getV();
    }

  // TODO 完成各种公司设置的 get 方法，就像上面两个方法一样
}
