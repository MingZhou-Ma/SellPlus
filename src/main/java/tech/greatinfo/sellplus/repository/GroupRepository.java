package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.group.Group;

/**
 * Created by Ericwyn on 18-7-23.
 */
public interface GroupRepository extends JpaRepository<Group, Long>,
        JpaSpecificationExecutor<Group> {
    List<Group> findAllByCustomerAndActivityNotNull(Customer customer);

    List<Group> findAllByActivityId(Long activityId);
}
