package cc.maid.lms.model;

/**
*Created by Abubakar Adamu on 06/04/2024
**/
public enum ResponseCodes {

    SUCCESS("00", "Successful"),
    BAD_REQUEST("104", "Bad Request"),
    NOT_FOUND("404", "Not record found");

    private final String code;
    private final String message;

    ResponseCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
