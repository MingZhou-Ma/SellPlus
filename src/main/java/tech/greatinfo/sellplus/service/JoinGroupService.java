package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.group.Group;
import tech.greatinfo.sellplus.domain.group.JoinGroup;
import tech.greatinfo.sellplus.repository.JoinGroupRepository;

/**
 * Created by Ericwyn on 18-7-27.
 */
@Service
public class JoinGroupService {
    @Autowired
    JoinGroupRepository joinGroupRepository;

    public JoinGroup save(JoinGroup joinGroup){
        return joinGroupRepository.save(joinGroup);
    }

    public JoinGroup findOne(Long id){
        return joinGroupRepository.findOne(id);
    }

    public JoinGroup findByCustomerAndGroup(Customer customer, Group group){
        return joinGroupRepository.findByCustomerAndGroup(customer, group);
    }
}
