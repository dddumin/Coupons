package springtest.exceptions;

public class IncorrectHashException extends Exception {
    public IncorrectHashException() {
        super();
    }

    public IncorrectHashException(String message) {
        super(message);
    }
}
