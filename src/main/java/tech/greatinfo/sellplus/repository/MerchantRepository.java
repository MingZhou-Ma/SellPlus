package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.Merchant;


/**
 * Created by Ericwyn on 18-7-23.
 */
public interface MerchantRepository extends JpaRepository<Merchant, Long>,
        JpaSpecificationExecutor<Merchant> {
    Merchant findByAccountAndPassword(String account, String password);
}
