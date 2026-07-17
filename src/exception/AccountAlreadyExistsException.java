package exception;

public class AccountAlreadyExistsException extends BankException {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
