package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.QRcode;


/**
 * Created by Ericwyn on 18-7-31.
 */
public interface QRcodeRepository extends JpaRepository<QRcode, Long>,
        JpaSpecificationExecutor<QRcode> {
    QRcode findBySceneAndPage(String scene, String page);

    QRcode findFirstByCustomerOrderBySceneDesc(Customer customer);


}
