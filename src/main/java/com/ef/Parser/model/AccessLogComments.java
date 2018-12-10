package com.ef.Parser.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "log_comment")
public class AccessLogComments extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "comment")
	private String comment;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


}
