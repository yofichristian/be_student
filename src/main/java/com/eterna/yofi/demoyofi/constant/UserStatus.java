package com.eterna.yofi.demoyofi.constant;

public enum UserStatus {

	ACTIVE(1,  "Active"),
	LOCKED(2,  "Locked"),
	DELETED(9,  "Deleted"),
	;

	private Integer code;
	private String description;

	UserStatus(Integer code, String description){
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static UserStatus getByCode(final Integer codeint) {
		for (UserStatus d : UserStatus.values()) {
			if (d.getCode().equals(codeint)) {
				return d;
			}
		}
		return null;
	}

}