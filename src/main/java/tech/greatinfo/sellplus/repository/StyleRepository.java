package tech.greatinfo.sellplus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.greatinfo.sellplus.domain.Style;

import java.util.List;

public interface StyleRepository extends JpaRepository<Style, Long>,
        JpaSpecificationExecutor<Style> {

    Page<Style> findAllByType(Integer type, Pageable pageable);

    List<Style> findAllByType(Integer type);


}
