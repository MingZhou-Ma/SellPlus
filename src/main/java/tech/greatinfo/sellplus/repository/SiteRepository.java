package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.greatinfo.sellplus.domain.Site;

/**
 * 场地
 */
public interface SiteRepository extends JpaRepository<Site, Long>,
        JpaSpecificationExecutor<Site> {

}
