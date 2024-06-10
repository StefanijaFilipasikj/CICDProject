package mk.ukim.finki.wp.eshop.service.impl;

import mk.ukim.finki.wp.eshop.model.Product;
import mk.ukim.finki.wp.eshop.model.ShoppingCart;
import mk.ukim.finki.wp.eshop.model.User;
import mk.ukim.finki.wp.eshop.model.exceptions.ProductAlreadyInShoppingCartException;
import mk.ukim.finki.wp.eshop.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.wp.eshop.model.exceptions.ShoppingCartNotFoundException;
import mk.ukim.finki.wp.eshop.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp.eshop.repository.ProductRepository;
import mk.ukim.finki.wp.eshop.repository.ShoppingCartRepository;
import mk.ukim.finki.wp.eshop.repository.UserRepository;
import mk.ukim.finki.wp.eshop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productService;

    public ShoppingCartImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ProductRepository productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long cartId) {
        if(this.shoppingCartRepository.findById(cartId).isEmpty()){
            throw new ShoppingCartNotFoundException();
        }
        return this.shoppingCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        ShoppingCart shoppingCart = user.getCart();
        Product product = this.productService.findById(productId).orElseThrow(ProductNotFoundException::new);
        if(shoppingCart.getProducts().stream().filter(p -> p.getId().equals(productId)).collect(Collectors.toList()).isEmpty()){
            shoppingCart.getProducts().add(product);
            return this.shoppingCartRepository.save(shoppingCart);
        }else{
            throw new ProductAlreadyInShoppingCartException();
        }
    }

    @Override
    public void removeFromCart(String username, Long id) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        ShoppingCart shoppingCart = user.getCart();
        shoppingCart.getProducts().removeIf(p -> p.getId().equals(id));
        this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getShoppingCart(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if(user.getCart() == null){
            ShoppingCart cart = new ShoppingCart(LocalDateTime.now());
            shoppingCartRepository.save(cart);
            user.setCart(cart);
            userRepository.save(user);
        }
        return user.getCart();
    }
}
