package com.ef.Parser.repositories;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ef.Parser.model.LogEntry;

@Repository
public interface AccessLogRepository extends JpaRepository<LogEntry, Long>{
    @Modifying
	@Transactional
	@Query (value="LOAD DATA LOCAL INFILE 'C:/Parser/target/classes/access.log' INTO TABLE access_log_entries FIELDS TERMINATED BY '|' LINES TERMINATED BY '\\n'", nativeQuery = true)
	public void loadDataFromFile();

}
