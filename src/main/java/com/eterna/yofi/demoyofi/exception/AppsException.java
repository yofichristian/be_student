package com.eterna.yofi.demoyofi.exception;

public class AppsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    //	private String errorCode = "00";
    private String errorCode = "99";
//    private BaseObject data = null;
    private Object data = null;
//	private HttpStatus httpStatus = HttpStatus.OK;

    public AppsException(String message) {
        super(message);
    }


    public AppsException(String message, Object data) {
        super(message);
		this.data = data;
    }

//	public AppsException(String errorCode, String errorDesc) {
//		super(errorDesc);
//		this.errorCode = errorCode;
//	}

//	public AppsException(String errorCode, String errorDesc, Object data) {
//		super(errorDesc);
//		this.errorCode = errorCode;
//		this.data = (BaseObject) data;
//	}
//
//	public AppsException(String errorCode, String errorDesc, Object data, HttpStatus httpStatus) {
//		super(errorDesc);
//		this.errorCode = errorCode;
//		this.data = (BaseObject) data;
//		this.httpStatus = httpStatus;
//	}
//
//	public AppsException(AppErrorCode appErrorCode, Object data ) {
//		super(appErrorCode.getDescription());
//		this.errorCode = appErrorCode.getCode();
//		this.data = (BaseObject) data;
//	}
//
//	public AppsException(AppErrorCode appErrorCode, String errorDesc, Object data ) {
//		super(errorDesc);
//		this.errorCode = appErrorCode.getCode();
//		this.data = (BaseObject) data;
//	}

    public String getErrorCode() {
        return errorCode;
    }

    public Object getData() {
        return data;
    }

//	public HttpStatus getHttpStatus() {
//		return this.httpStatus;
//	}

}