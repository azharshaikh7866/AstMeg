package com.meganet.pan.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;

import com.meganet.pan.enums.OperationType;


@Entity
@Audited
public class RequestDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long requestId;
	
	@CreatedDate
	private Date requestedOn;
	
	@CreatedDate
	private String requestedBy;
	
	@Column
	private String source;
	
	@Column
	private String appUniqueId;

	@Column(length=3000)
	private String reqData;
	
	@Enumerated(EnumType.STRING)
	private OperationType type;

	public RequestDetail() {
		super();
	}

	public RequestDetail(Date requestedOn, String requestedBy, String source, String appUniqueId, String reqData,
			OperationType type) {
		super();
		this.requestedOn = requestedOn;
		this.requestedBy = requestedBy;
		this.source = source;
		this.appUniqueId = appUniqueId;
		this.reqData = reqData;
		this.type = type;
	}

	public Long getRequestId() {
		return requestId;
	}

	public Date getRequestedOn() {
		return requestedOn;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public String getSource() {
		return source;
	}

	public String getAppUniqueId() {
		return appUniqueId;
	}

	public String getReqData() {
		return reqData;
	}

	public OperationType getType() {
		return type;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public void setRequestedOn(Date requestedOn) {
		this.requestedOn = requestedOn;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setAppUniqueId(String appUniqueId) {
		this.appUniqueId = appUniqueId;
	}

	public void setReqData(String reqData) {
		this.reqData = reqData;
	}

	public void setType(OperationType type) {
		this.type = type;
	}
	
}
