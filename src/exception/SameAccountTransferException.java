package exception;

public class SameAccountTransferException extends BankException{
    public SameAccountTransferException(String message) {
        super(message);
    }
}
