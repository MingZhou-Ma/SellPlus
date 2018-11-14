package tech.greatinfo.sellplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.greatinfo.sellplus.domain.MsgRecord;

public interface MsgRecordRepository extends JpaRepository<MsgRecord, Long>,
        JpaSpecificationExecutor<MsgRecord> {

}
