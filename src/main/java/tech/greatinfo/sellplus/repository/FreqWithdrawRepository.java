package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.greatinfo.sellplus.domain.FreqWithdraw;

public interface FreqWithdrawRepository extends JpaRepository<FreqWithdraw, Long>,
        JpaSpecificationExecutor<FreqWithdraw> {

}
