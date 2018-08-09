package com.meganet.pan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;

import com.meganet.pan.enums.OperationType;

@Entity
@Audited
public class ResponseDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long responseId;
	
	@CreatedDate
	private Date receivedOn;
	
	@OneToOne
	@JoinColumn(name="requestId")
	private RequestDetail requestData;
	
	@Column
	private String appUniqueId;
	
	@Lob
	private byte[] respData;
	
	@Column
	private String contentType;
	
	@Column
	private String latency;

	@Enumerated(EnumType.STRING)
	private OperationType type;

	public ResponseDetail() {
		super();
	}

	public ResponseDetail(Date receivedOn, RequestDetail requestData, String appUniqueId, byte[] respData,
			String contentType, String latency, OperationType type) {
		super();
		this.receivedOn = receivedOn;
		this.requestData = requestData;
		this.appUniqueId = appUniqueId;
		this.respData = respData;
		this.contentType = contentType;
		this.latency = latency;
		this.type = type;
	}

	public Date getReceivedOn() {
		return receivedOn;
	}

	public RequestDetail getRequestData() {
		return requestData;
	}

	public String getAppUniqueId() {
		return appUniqueId;
	}

	public byte[] getRespData() {
		return respData;
	}

	public String getContentType() {
		return contentType;
	}

	public String getLatency() {
		return latency;
	}

	public OperationType getType() {
		return type;
	}

	public void setReceivedOn(Date receivedOn) {
		this.receivedOn = receivedOn;
	}

	public void setRequestData(RequestDetail requestData) {
		this.requestData = requestData;
	}

	public void setAppUniqueId(String appUniqueId) {
		this.appUniqueId = appUniqueId;
	}

	public void setRespData(byte[] respData) {
		this.respData = respData;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setLatency(String latency) {
		this.latency = latency;
	}

	public void setType(OperationType type) {
		this.type = type;
	}

}
