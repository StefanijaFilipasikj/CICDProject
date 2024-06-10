package mk.ukim.finki.wp.eshop.model.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException() {
        super("Username already exists");
    }
}
