package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.group.Group;
import tech.greatinfo.sellplus.repository.GroupRepository;


/**
 * Created by Ericwyn on 18-7-27.
 */
@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;

    public Group save(Group group){
        return groupRepository.save(group);
    }

    public Group findOne(Long id){
        return groupRepository.findOne(id);
    }

    public List<Group> findAllByCustomer(Customer customer){
        return groupRepository.findAllByCustomer(customer);
    }
}
