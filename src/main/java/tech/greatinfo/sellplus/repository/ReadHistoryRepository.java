package tech.greatinfo.sellplus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.domain.article.ReadHistory;

/**
 * Created by Ericwyn on 18-8-29.
 */
public interface ReadHistoryRepository extends JpaRepository<ReadHistory, Long>,
        JpaSpecificationExecutor<ReadHistory> {
    Page<ReadHistory> findAllByCustom_SellerOrderByReadTimeDesc(Seller seller, Pageable pageable);
}
