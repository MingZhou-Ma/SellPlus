package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tech.greatinfo.sellplus.domain.help.Help;
import tech.greatinfo.sellplus.domain.help.HelpHistory;


/**
 * Created by Ericwyn on 18-7-23.
 */
public interface HelpHistoryRepository extends JpaRepository<HelpHistory, Long>,
        JpaSpecificationExecutor<HelpHistory> {
    void deleteAllByHelp(Help help);
}
