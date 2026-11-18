package com.eterna.yofi.demoyofi.constant;

public class SystemConstant {

    public static final String ENTITY_NO = "entityNo";
    //    public static final String SESSION_ID = "Session_id";
    public static final String HEADER_CLIENT_IP = "X-Forwarded-For";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String USERID = "Userid";
    public static final String FULLNAME = "Fullname";
    public static final String HOST = "Host";
    public static final String BROWSER = "Browser";
    public static final String PLATFORM = "Platform";
    public static final String OSNAME = "Osname";
    public static final String MENU = "Menu";
    public static final String DEVICE = "Device";
    public static final String SERVICES = "Services";
    public static final String X_REQUEST_ID = "X-Request-Id";
    public static final String X_REAL_IP = "X-Real-IP";

    public static final String MSG_RUN_ERROR = "run error {}";
    public static final String ERROR_MESSAGE = "error_message";

    public static final String CELL_FORMAT_DOUBLE = "#,##0.00";
    public static final String CELL_FORMAT_PERCENT = "0.00%";
    public static final String CELL_FORMAT_DATE = "yyyy-mm-dd";

    public static final String MIMETYPE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static final String APPROVE = "APPROVE";
    public static final String REJECT = "REJECT";

    protected SystemConstant() {
        throw new IllegalStateException("System Utility class");
    }

}
