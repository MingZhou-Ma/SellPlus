package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.LotteryShare;


public interface LotteryShareRepository extends JpaRepository<LotteryShare, Long>,
        JpaSpecificationExecutor<LotteryShare> {

    LotteryShare findByOriginAndOwn(Customer origin, Customer own);

}
