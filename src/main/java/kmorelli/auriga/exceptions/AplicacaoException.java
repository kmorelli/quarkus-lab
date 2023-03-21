package kmorelli.auriga.exceptions;

public class AplicacaoException extends RuntimeException {
    
    final private String code;
    final private String message;

    public AplicacaoException( String code, String message, Throwable e ) {
        super(e);
        this.code = code;
        this.message = message;
    }

    public AplicacaoException( String code, String message) {
        super();
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
