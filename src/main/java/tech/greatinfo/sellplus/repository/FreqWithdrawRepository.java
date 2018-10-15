package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.FreqWithdraw;

import java.util.List;

public interface FreqWithdrawRepository extends JpaRepository<FreqWithdraw, Long>,
        JpaSpecificationExecutor<FreqWithdraw> {

    List<FreqWithdraw> findAllByCustomer(Customer customer);

}
