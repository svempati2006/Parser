package com.ef.Parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ef.Parser.dto.RequestDto;
import com.ef.Parser.model.AccessLogComments;
import com.ef.Parser.model.LogEntry;
import com.ef.Parser.services.ParserService;
import com.ef.Parser.util.ParserUtil;
import com.mysql.jdbc.StringUtils;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@SpringBootApplication
public class Parser implements ApplicationRunner{

	@Autowired
	ParserService parserService;
	
	
	public static void main(String[] args) {
		SpringApplication.run(Parser.class, args);
	}
	
	//@Override
    public void run(ApplicationArguments args) throws Exception {
     	RequestDto request = new RequestDto(args);
    	if(!request.validRequest()) {
    		System.out.println("INVALID REQUEST PARAMETERS--PLEASE TRY AGAIN");
    		System.exit(0);
    	}
    	try {
    		File accessLog;
    		if(request.getAccessLog() != null && request.getAccessLog().length() >0) {
    			accessLog=new File(request.getAccessLog());
    		}else {
    			accessLog=ParserUtil.loadAccessFileFromClassPath();
    		}
		
			/**This call will delay the insertion of log entries.So not using at this time.
			//List<LogEntry> entries=parserService.getLogFromFile(accessLog);
			//parserService.saveLogs(entries);
			 * */
			
			parserService.loadData(accessLog.toPath());
			
		} catch (Exception e) {
			System.out.println("LOG FILE NOT FOUND IN CLASSPATH"+e.getMessage());
			System.exit(0);
		}
        List<LogEntry> logs = parserService.find(request);
        List<AccessLogComments> comments = parserService.saveLogComments(logs,request);
        comments.forEach(commentLog -> System.out.println(commentLog.getComment()));
    }
}
