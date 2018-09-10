package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Diary;
import tech.greatinfo.sellplus.repository.DiaryRepository;

import java.util.List;

/**
 * Created by Ericwyn on 18-9-8.
 */
@Service
public class DiaryService {
    @Autowired
    DiaryRepository repository;

    public Diary save(Diary diary){
        return repository.save(diary);
    }

    public Diary findOne(String diaryId){
        return repository.findByDiaryId(diaryId);
    }

    public List<Diary> findAllByCustomerAndGeneral(Customer customer, Boolean general) {
        return repository.findAllByCustomerAndGeneral(customer, general);
    }

    public Diary findFirstByCustomerAndGeneralOrderByGeneralTimeDesc(Customer customer, Boolean general) {
        return repository.findFirstByCustomerAndGeneralOrderByGeneralTimeDesc(customer, general);
    }
}
