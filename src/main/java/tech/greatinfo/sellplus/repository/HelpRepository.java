package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import tech.greatinfo.sellplus.domain.Activity;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.help.Help;


/**
 * Created by Ericwyn on 18-7-23.
 */
public interface HelpRepository extends JpaRepository<Help, Long>,
        JpaSpecificationExecutor<Help> {
    List<Help> findAllByCustomerAndActivityNotNull(Customer customer);
    List<Help> findAllByActivityId(Long activityId);
    Help findByCustomerAndActivity(Customer customer, Activity activity);
}
