package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.group.Group;
import tech.greatinfo.sellplus.domain.group.JoinGroup;


/**
 * Created by Ericwyn on 18-7-23.
 */
public interface JoinGroupRepository extends JpaRepository<JoinGroup, Long>,
        JpaSpecificationExecutor<JoinGroup> {
    JoinGroup findByCustomerAndGroup(Customer customer, Group group);
}
