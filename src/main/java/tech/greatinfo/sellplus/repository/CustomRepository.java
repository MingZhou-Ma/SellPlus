package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.Customer;

/**
 * Created by Ericwyn on 18-7-23.
 */
public interface CustomRepository extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
    Customer getByOpenid(String openId);

    Customer getByUid(String uid);
}
