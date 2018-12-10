package com.ef.Parser.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "access_log_entries")
public class LogEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "request_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestDate;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "request_method")
	private String requestMethod;

	@Column(name = "status_code")
	private String responseStatusCode;

	@Column(name = "user_agent")
	private String userAgent;

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date date) {
		this.requestDate = date;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getResponseStatusCode() {
		return responseStatusCode;
	}

	public void setResponseStatusCode(String responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

    @Override  
    public int hashCode() {  
        int hash = 0;  
        hash += (this.getRequestDate() != null ? this.getRequestDate().hashCode() : 0);  
  
        return hash;  
    }  
  
    @Override  
    public boolean equals(Object object) {  
    if (this == object)  
            return true;  
        if (object == null)  
            return false;  
        if (getClass() != object.getClass())  
            return false;  
  
        LogEntry other = (LogEntry) object;  
        if (this.getRequestDate() != other.getRequestDate() ) {  
            return false;  
        }  
        return true;  
    }  

}
