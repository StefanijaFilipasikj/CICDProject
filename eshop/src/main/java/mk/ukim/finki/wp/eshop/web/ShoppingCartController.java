package mk.ukim.finki.wp.eshop.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.eshop.model.Product;
import mk.ukim.finki.wp.eshop.model.ShoppingCart;
import mk.ukim.finki.wp.eshop.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error, HttpServletRequest request, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        String username = request.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getShoppingCart(username);
        model.addAttribute("products", this.shoppingCartService.listAllProductsInShoppingCart(shoppingCart.getId()));
        model.addAttribute("totalPrice", shoppingCart.getProducts().stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("bodyContent", "shopping-cart");
        return "master-template";
    }

    @PostMapping("/add-product/{id}")
    public String addProductToShoppingCart(@PathVariable Long id, HttpServletRequest request){
        try{
            String username = request.getRemoteUser();
            ShoppingCart shoppingCart = this.shoppingCartService.addProductToShoppingCart(username, id);
            return "redirect:/shopping-cart";
        }catch (RuntimeException exception){
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpServletRequest request){
        String username = request.getRemoteUser();
        this.shoppingCartService.removeFromCart(username, id);
        return "redirect:/shopping-cart";
    }
}
