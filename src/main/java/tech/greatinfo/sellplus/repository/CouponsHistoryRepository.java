package tech.greatinfo.sellplus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.CouponsHistory;

/**
 * Created by Ericwyn on 18-9-16.
 */
public interface CouponsHistoryRepository extends JpaRepository<CouponsHistory, Long>,
        JpaSpecificationExecutor<CouponsHistory> {
    Page<CouponsHistory> findAllBySeller(Seller seller, Pageable pageable);

    void deleteAllByCouponObj_Coupon(Coupon coupon);

}
