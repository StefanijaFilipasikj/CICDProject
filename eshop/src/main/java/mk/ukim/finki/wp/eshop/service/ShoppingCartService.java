package mk.ukim.finki.wp.eshop.service;

import mk.ukim.finki.wp.eshop.model.Product;
import mk.ukim.finki.wp.eshop.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<Product> listAllProductsInShoppingCart(Long cartId);
    ShoppingCart addProductToShoppingCart(String username, Long productId);
    void removeFromCart(String username, Long id);
    ShoppingCart getShoppingCart(String username);
}
