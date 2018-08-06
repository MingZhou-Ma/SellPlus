package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
