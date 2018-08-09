package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.repository.CustomRepository;

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
}
