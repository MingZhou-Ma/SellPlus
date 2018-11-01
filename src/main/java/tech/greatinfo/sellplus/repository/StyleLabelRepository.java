package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.greatinfo.sellplus.domain.StyleLabel;

public interface StyleLabelRepository extends JpaRepository<StyleLabel, Long>,
        JpaSpecificationExecutor<StyleLabel> {


}
