package com.eterna.yofi.demoyofi.constant;

public enum AppErrorCode {

	SUCCESS("00","Approved"),
	ERROR("99","General Error"),
	USERNAME_IS_EXISTS("51","Username is already exit"),
	USERNAME_IS_NOT_FOUND("52","User is not found"),
	PASSWORD_IS_NOT_CORRECT("53","Password is not correct"),

	TIMEOUT("98", "Transaction time out"),
	;

	private String code;

	private String description;

	AppErrorCode(String code, String description){
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static AppErrorCode getByCode(final String code) {
		for (AppErrorCode d : AppErrorCode.values()) {
			if (d.getCode().equalsIgnoreCase(code)) {
				return d;
			}
		}
		return null;
	}
}