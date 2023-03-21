package kmorelli.auriga.exceptions;

public class ExtensionException extends RuntimeException {
    
    final private int code;
    final private String message;

    public ExtensionException( int code, String message, Throwable e ) {
        super(e);
        this.code = code;
        this.message = message;
    }

    public ExtensionException( int code, String message) {
        super();
        this.code = code;
        this.message = message;     
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
