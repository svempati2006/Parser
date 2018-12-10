package com.ef.Parser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ef.Parser.model.AccessLogComments;

@Repository
public interface AccessLogCommentsRepository extends JpaRepository<AccessLogComments, Long>{

}
