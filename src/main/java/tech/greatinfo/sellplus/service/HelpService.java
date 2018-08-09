package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.help.Help;
import tech.greatinfo.sellplus.repository.HelpRepository;

/**
 *
 * 助力活动 service
 * Created by Ericwyn on 18-7-30.
 */
@Service
public class HelpService {
    @Autowired
    HelpRepository helpRepository;

    public void save(Help help){
        helpRepository.save(help);
    }

    public Help findOne(Long id){
        return helpRepository.findOne(id);
    }

    public List<Help> findAllByCustomer(Customer customer){
        return helpRepository.findAllByCustomerAndActivityNotNull(customer);
    }

    public List<Help> findAllByActivity(Long activityId){
        return helpRepository.findAllByActivityId(activityId);
    }

}
