package tech.greatinfo.sellplus.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.repository.CouponsObjRepository;

/**
 * Created by Ericwyn on 18-9-6.
 */
@Service
public class CouponsObjService {
    @Autowired
    CouponsObjRepository objRepository;

    public CouponsObj save(CouponsObj obj){
        return objRepository.save(obj);
    }

    public void save(List<CouponsObj> obj){
        objRepository.save(obj);
    }

    public CouponsObj findOne(Long id){
        return objRepository.findOne(id);
    }

    public Page<CouponsObj> findAll(int start, int num){
        return objRepository.findAll(new PageRequest(start,num));
    }

    public void deleteByMode(Coupon model){
        objRepository.deleteAllByCoupon(model);
    }

    public Long countAllByOrigin(Customer origin){
        return objRepository.countAllByOrigin(origin);
    }

    public Long countAllByOriginAndExpiredTrue(Customer origin){
        return objRepository.countAllByOriginAndExpiredTrue(origin);
    }

    public Page<CouponsObj> getAllByOwn(Customer own,int start,int num){
        return objRepository.getAllByOwn(own,new PageRequest(start,num));
    }

    /**
     * 生成卷 code ，由 大小写，和 0 ~ 9 组成的 6 位数字
     * @return
     */
    public String getRandomCouponCode(){
        int temp;
        int temp2;
        char ch;
        String res = "";
        for (int i=0;i<8;i++){
            temp = (int)(Math.random()*3);
            switch (temp){
                case 0:
                    res += (int)(Math.random()*10);
                    break;
                case 1:
                    res += (char)((int)(Math.random()*26)+65);
                    break;
                case 2:
                    res += (char)((int)(Math.random()*26)+97);
                    break;
            }
        }
        return res;
    }
}
