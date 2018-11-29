package fr.abberrouyne.kata.exception;

public class KataCommonsException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public KataCommonsException() {
        super();
    }

    /**
     * Exception with message
     * 
     * @param message
     *            the message of the exception
     */
    public KataCommonsException(String message) {
        super(message);
    }

    /**
     * Exception with message and original exception
     * 
     * @param message
     *            the message of the exception
     * @param throwable
     *            original exception
     */
    public KataCommonsException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
