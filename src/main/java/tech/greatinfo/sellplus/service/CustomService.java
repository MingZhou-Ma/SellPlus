package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.repository.CustomRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by Ericwyn on 18-7-23.
 */
@Service
public class CustomService {
    @Autowired
    CustomRepository repository;

    public Customer save(Customer customer){
         return repository.save(customer);
    }

    public Customer getByOpenId(String openId){
        return repository.getByOpenid(openId);
    }

    public void update(Customer oldCustom, Customer customer){
        Field[] fields = customer.getClass().getDeclaredFields();
        for (Field field:fields){
            try {
                boolean access = field.isAccessible();
                if(!access) field.setAccessible(true);
                Object o = field.get(customer);
                //静态变量不操作,这样的话才不会报错
                if (o!=null && !Modifier.isStatic(field.getModifiers())){
                    field.set(oldCustom,o);
                }
                if(!access) field.setAccessible(false);
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        repository.saveAndFlush(oldCustom);
    }

    public Customer getOne(Long id){
        return repository.getOne(id);
    }

    public Customer getByUid(String uid){
        return repository.getByUid(uid);
    }

    public Page<Customer> getAllBySeller(Seller seller, Pageable pageable){
        return repository.getAllBySellerAndPhoneNotNull(seller, pageable);
    }

    public Page<Customer> getAllBySellerOrderByCreateTimeDesc(Seller seller, Pageable pageable) {
        return repository.getAllBySellerOrderByCreateTimeDesc(seller, pageable);
    }

    public Page<Customer> getAllBySellerAndAccessRecordOrderByCreateTimeDesc(Seller seller, String accessRecord, Pageable pageable) {
        return repository.getAllBySellerAndAccessRecordOrderByCreateTimeDesc(seller, accessRecord, pageable);
    }

    public Page<Customer> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Page<Customer> getAllByOrderByCreateTimeDesc(Pageable pageable){
        //return repository.getAllByOrderByCreateTimeDesc(pageable);
        return repository.getAllByPhoneNotNullOrderByCreateTimeDesc(pageable);
    }

    public List<Customer> findBySeller(Long sellerId){
        return repository.getAllBySellerId(sellerId);
    }

    public long count() {
        return repository.count();
    }

    public List<Customer> findAllCustomer() {
        return repository.findAll();
    }

    public List<Customer> getAllByPhoneNotNull() {
        return repository.getAllByPhoneNotNull();
    }

//    public Page<Customer> getAllBySellerAndOriginOrderByCreateTimeDesc(Seller seller, String origin, Pageable pageable) {
//        return repository.getAllBySellerAndOriginOrderByCreateTimeDesc(seller, origin, pageable);
//    }
}
