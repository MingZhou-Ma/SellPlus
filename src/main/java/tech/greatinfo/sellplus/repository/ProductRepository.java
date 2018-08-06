package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.Product;


/**
 * Created by Ericwyn on 18-7-23.
 */
public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

}
