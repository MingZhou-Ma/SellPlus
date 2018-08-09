package tech.greatinfo.sellplus.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import tech.greatinfo.sellplus.domain.Activity;


/**
 * Created by Ericwyn on 18-7-23.
 */
public interface ActivityRepository extends JpaRepository<Activity, Long>,
        JpaSpecificationExecutor<Activity> {
    Page<Activity> getAllByIsGroupTrue(Pageable pageable);
    Page<Activity> getAllByIsGroupFalse(Pageable pageable);

    List<Activity> getAllByProductId(Long productId);
}
