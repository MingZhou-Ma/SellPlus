package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.SellerCode;

import java.util.List;


/**
 * Created by Ericwyn on 18-7-23.
 */
public interface SellerCodeRepository extends JpaRepository<SellerCode, Long>,
        JpaSpecificationExecutor<SellerCode> {

    List<SellerCode> findAllByCustomer(Customer customer);

    SellerCode findBySceneAndPage(String scene, String page);

    SellerCode findByScene(String scene);

}
