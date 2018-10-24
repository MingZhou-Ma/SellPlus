package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.repository.CouponsHistoryRepository;
import tech.greatinfo.sellplus.repository.CouponsObjRepository;
import tech.greatinfo.sellplus.repository.CouponsRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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

    public void updateCoupon(Coupon oldEntity, Coupon newEntity){
        Field[] fields = newEntity.getClass().getDeclaredFields();
        for (Field field:fields){
            try {
                boolean access = field.isAccessible();
                if(!access) field.setAccessible(true);
                Object o = field.get(newEntity);
                //静态变量不操作,这样的话才不会报错
                if (o!=null && !Modifier.isStatic(field.getModifiers())){
                    field.set(oldEntity,o);
                }
                if(!access) field.setAccessible(false);
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        couponsRepository.saveAndFlush(oldEntity);
    }
}
