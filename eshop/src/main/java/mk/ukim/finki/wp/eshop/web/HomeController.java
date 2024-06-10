package mk.ukim.finki.wp.eshop.web;

import mk.ukim.finki.wp.eshop.model.Product;
import mk.ukim.finki.wp.eshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("home")
    public String getHomePage(Model model){
        model.addAttribute("products", productService.findTop3Products());
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }

    @GetMapping("access_denied")
    public String getAccessDeniesPage(Model model){
        model.addAttribute("bodyContent", "access-denied");
        return "master-template";
    }
}
