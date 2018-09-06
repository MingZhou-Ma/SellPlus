package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.coupons.Coupon;

/**
 * Created by Ericwyn on 18-9-6.
 */
public interface CouponsRepository extends JpaRepository<Coupon, Long>,
        JpaSpecificationExecutor<Coupon> {

}
