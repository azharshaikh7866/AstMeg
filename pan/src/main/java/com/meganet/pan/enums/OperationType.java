package com.meganet.pan.enums;

public enum OperationType {
	
	NETBANKING(1,"NETBANKING"),
	STATEMENTUPLOAD(2,"STATEMENTUPLOAD"),
	INSTITUTIONLIST(3,"INSTITUTIONLIST"),
	PAYLOAD(4,"PAYLOAD");
	
	
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	private OperationType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public static OperationType fromInt(int id) {
		switch (id) {
		case 1:
			return NETBANKING;
		case 2:
			return STATEMENTUPLOAD;
		case 3:
			return INSTITUTIONLIST;
		case 4:
			return PAYLOAD;
		default:
			return null;
		}
	}

}
