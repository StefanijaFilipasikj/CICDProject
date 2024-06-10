package mk.ukim.finki.wp.eshop.model.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User not found exception");
    }
}
