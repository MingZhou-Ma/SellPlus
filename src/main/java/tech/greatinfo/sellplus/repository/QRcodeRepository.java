package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.QRcode;

import java.util.List;


/**
 * Created by Ericwyn on 18-7-31.
 */
public interface QRcodeRepository extends JpaRepository<QRcode, Long>,
        JpaSpecificationExecutor<QRcode> {
    QRcode findBySceneAndPage(String scene, String page);

    QRcode findBySceneAndPageAndType(String scene, String page, String type);

    QRcode findFirst1ByCustomerOrderBySceneDesc(Customer customer);

    QRcode findFirst1ByCustomerAndTypeOrderBySceneDesc(Customer customer, String type);
    //QRcode findTopByCustomerOrderBySceneDesc(Customer customer);

    List<QRcode> findAllByCustomerAndType(Customer customer, String type);

    QRcode findBySceneAndType(String scene, String type);
}

