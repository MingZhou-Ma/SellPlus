package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Diary;

/**
 * Created by Ericwyn on 18-7-23.
 */
public interface DiaryRepository extends JpaRepository<Diary, Long>,
        JpaSpecificationExecutor<Diary> {
    Diary findByDiaryId(String diaryId);

    Diary findFirstByCustomerAndGeneralTrueOrderByGeneralTimeDesc(Customer customer);
}
