package model;

/**
 * Wyjątek występuje kiedy użytkownik wprowadzi błędne dane do formularza
 */
public class WrongEventValueException extends Exception {
	private static final long serialVersionUID = 2134961333402609268L;

	public WrongEventValueException(String msg) {
        super(msg);
    }
}
