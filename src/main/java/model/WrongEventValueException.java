package model;

/**
 * Wyjątek występuje kiedy użytkownik wprowadzi błędne dane do formularza
 */
public class WrongEventValueException extends Exception {
    public WrongEventValueException(String msg) {
        super(msg);
    }
}
