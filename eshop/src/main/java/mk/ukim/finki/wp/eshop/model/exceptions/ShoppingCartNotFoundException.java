package mk.ukim.finki.wp.eshop.model.exceptions;

public class ShoppingCartNotFoundException extends RuntimeException{

    public ShoppingCartNotFoundException() {
        super("Invalid shopping cart exception");
    }
}
