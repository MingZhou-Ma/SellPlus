package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.Seller;

/**
 * Created by Ericwyn on 18-8-14.
 */
public interface SellerRepository extends JpaRepository<Seller, Long>,
        JpaSpecificationExecutor<Seller> {
    Seller findByAccountAndSellerKey(String account, String sellerKey);
}
