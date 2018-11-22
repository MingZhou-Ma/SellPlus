package tech.greatinfo.sellplus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Seller;

import java.util.List;

/**
 * Created by Ericwyn on 18-7-23.
 */
public interface CustomRepository extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
    Customer getByOpenid(String openId);

    Customer getByUid(String uid);

    Page<Customer> getAllBySeller(Seller seller, Pageable pageable);

    Page<Customer> getAllBySellerOrderByCreateTimeDesc(Seller seller, Pageable pageable);

    Page<Customer> getAllBySellerAndAccessRecordOrderByCreateTimeDesc(Seller seller, String accessRecord, Pageable pageable);

    List<Customer> getAllBySellerId(Long sellerId);

    Page<Customer> getAllByOrderByCreateTimeDesc(Pageable pageable);

    @Query(value = "select count(*) from customer where month(create_time) = month(now())", nativeQuery = true)
    Long getThisMonthCustomerNum();
}
