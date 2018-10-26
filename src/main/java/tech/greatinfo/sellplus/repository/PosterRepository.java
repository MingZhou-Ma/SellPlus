package tech.greatinfo.sellplus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.greatinfo.sellplus.domain.Poster;

import java.util.List;

public interface PosterRepository extends JpaRepository<Poster, Long>,
        JpaSpecificationExecutor<Poster> {

    Page<Poster> findAllByType(Integer type, Pageable pageable);

    Page<Poster> findAllByTypeAndIsPoster(Integer type, Integer isPoster, Pageable pageable);

    List<Poster> findAllByTypeAndIsPoster(Integer type, Integer isPoster);

}
