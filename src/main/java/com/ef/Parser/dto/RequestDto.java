package com.ef.Parser.dto;

import org.springframework.boot.ApplicationArguments;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RequestDto {

	private Long threshold=100l;
	private Date startDate=new Date();
	private Date endDate=new Date();
	private String accessLog;

	private ApplicationArguments args;

	public RequestDto(ApplicationArguments args) throws ParseException {
		this.args = args;
		if (args.containsOption("startDate")) {
			String date = args.getOptionValues("startDate").get(0);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd.hh:mm:ss");
			this.startDate = formatter.parse(date);
			this.endDate = startDate;
		}
		if (args.containsOption("duration")) {
			String duration = args.getOptionValues("duration").get(0);
			if (duration.equals("hourly")) {
				this.endDate = new Date(this.startDate.getTime() + TimeUnit.HOURS.toMillis(1));
			} else if (duration.equals("daily")) {
				this.endDate = new Date(this.startDate.getTime() + TimeUnit.DAYS.toMillis(1));
			}
		}
		if (args.containsOption("threshold")) {
			this.threshold = new Long(args.getOptionValues("threshold").get(0));
		}
		if (args.containsOption("accesslog")) {
			this.accessLog = new String(args.getOptionValues("accesslog").get(0));
		}
		
	}
	

	public String getAccessLog() {
		return accessLog;
	}


	public void setAccessLog(String accessLog) {
		this.accessLog = accessLog;
	}


	public Long getThreshold() {
		return threshold;
	}


	public Date getStartDate() {
		return startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public ApplicationArguments getArgs() {
		return args;
	}


	public boolean validRequest() {
		return (this.args.containsOption("startDate") && this.args.containsOption("duration")
				&& this.args.containsOption("threshold"));
	}
}