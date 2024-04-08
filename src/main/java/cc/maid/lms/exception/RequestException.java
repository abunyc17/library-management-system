package cc.maid.lms.exception;

/**
 * Created by Abubakar Adamu on 05/04/2024
 **/
public class RequestException extends RuntimeException{

    private String message;

    public RequestException(String message) {
        this.message = message;
    }

    public RequestException(String code, String message) {
        super(code);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
