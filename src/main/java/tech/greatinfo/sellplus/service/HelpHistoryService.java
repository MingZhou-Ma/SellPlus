package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.help.HelpHistory;
import tech.greatinfo.sellplus.repository.HelpHistoryRepository;


/**
 *
 * 助力历史 service
 * Created by Ericwyn on 18-7-30.
 */
@Service
public class HelpHistoryService {
    @Autowired
    HelpHistoryRepository historyRepository;

    public void save(HelpHistory history){
        historyRepository.save(history);
    }

    public HelpHistory findOne(Long id){
        return historyRepository.findOne(id);
    }
}
