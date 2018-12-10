package com.ef.Parser.services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.Parser.dto.RequestDto;
import com.ef.Parser.model.AccessLogComments;
import com.ef.Parser.model.LogEntry;
import com.ef.Parser.repositories.AccessLogCommentsRepository;
import com.ef.Parser.repositories.AccessLogRepository;
import com.ef.Parser.repositories.CustomLogRepositoryImpl;

@Service
public class ParserService {

	@Autowired
    private AccessLogRepository logRepository;
	
    @Autowired
    private AccessLogCommentsRepository commentLogRepository;
    
    @Autowired
    private CustomLogRepositoryImpl customRepository;
    
    public List<LogEntry> getLogFromFile(File file) throws IOException, ParseException{
        List<LogEntry> logs = new ArrayList<>();
        Reader in = new FileReader(file);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withDelimiter('|').parse(in);
        for (CSVRecord record : records) {
        	LogEntry log = new LogEntry();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            String dateString = record.get(0);
            dateString = dateString.replace("\uFEFF", ""); // remove UTF BOM
            Date date = formatter.parse(dateString);
            log.setRequestDate(date);
            log.setIpAddress(record.get(1));
            log.setRequestMethod(record.get(2));
            log.setResponseStatusCode(record.get(3));
            log.setUserAgent(record.get(4));
            logs.add(log);
        }
        return logs;
    }

    //Using this will delay the loading..
    public void saveLogs(List<LogEntry> logs) {
    	
    	for(LogEntry l:logs) {
    		logRepository.save(l);
    	}

    } 
    
    public void cleanUpPreviousEntries() {
    	//Remove deleteAll when we implement this in real time.Need to rewrite the log records.
    	
    	try {
			customRepository.deleteAllMessages();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
   // @Transactional
    public List<LogEntry> find(RequestDto request) throws Exception{
		return customRepository.find(request);
    	
    }
    
    public void loadData(Path path) {
    	//Clean the previous--Comment the code if not needed..
    	cleanUpPreviousEntries();
    	System.out.println("Deleted");
    	logRepository.loadDataFromFile();
    	System.out.println("Loaded");
    }
    
   // @Transactional
    public List<AccessLogComments> saveLogComments(List<LogEntry> logs,RequestDto dto) {
        List<AccessLogComments> commentLogs = new ArrayList<>();
        logs.forEach(log -> {
        	AccessLogComments commentLog = new AccessLogComments();
            commentLog.setIpAddress(log.getIpAddress());
            commentLog.setComment("The IP: " + log.getIpAddress() + " has reached more than " + dto.getThreshold().toString() +
            " requests between " + dto.getStartDate().toString() + " and " + dto.getEndDate().toString());
            commentLogs.add(commentLog);
        });
        commentLogRepository.saveAll(commentLogs);
     return commentLogs;
    }

}
