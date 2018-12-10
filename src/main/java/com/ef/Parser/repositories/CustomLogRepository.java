package com.ef.Parser.repositories;

import com.ef.Parser.model.LogEntry;

import java.util.List;

import com.ef.Parser.dto.RequestDto;
public interface CustomLogRepository {

	public void deleteAllMessages() throws Exception ;
	public List<LogEntry> find(RequestDto params) throws Exception;
}
