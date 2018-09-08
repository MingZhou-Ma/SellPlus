package tech.greatinfo.sellplus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;

/**
 * Created by Ericwyn on 18-9-6.
 */
public interface CouponsObjRepository extends JpaRepository<CouponsObj, Long>,
        JpaSpecificationExecutor<CouponsObj> {
    void deleteAllByCoupon(tech.greatinfo.sellplus.domain.coupons.Coupon coupon);

    Long countAllByOrigin(Customer origin);

    Long countAllByOriginAndExpiredTrue(Customer origin);

    Page<CouponsObj> getAllByOwn(Customer customer,Pageable pageable);
}
