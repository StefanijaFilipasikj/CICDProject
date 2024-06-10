package mk.ukim.finki.wp.eshop.web;

import mk.ukim.finki.wp.eshop.model.Category;
import mk.ukim.finki.wp.eshop.model.Manufacturer;
import mk.ukim.finki.wp.eshop.service.CategoryService;
import mk.ukim.finki.wp.eshop.service.ManufacturerService;
import mk.ukim.finki.wp.eshop.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    public ProductController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getProductsPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("products", productService.findAll());
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        this.productService.delete(id);
        return "redirect:/products";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model){
        List<Category> categories = this.categoryService.listCategories();
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("bodyContent", "add-product");

        return "master-template";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model){

        if(this.productService.findById(id).isPresent()){
            model.addAttribute("product", this.productService.findById(id).get());
            List<Category> categories = this.categoryService.listCategories();
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();

            model.addAttribute("categories", categories);
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("bodyContent", "edit-product");

            return "master-template";
        }
        return "redirect:/products?error=ProductNotFound";
    }

    @PostMapping("/add")
    public String saveProduct(@RequestParam String name,
                              @RequestParam Double price,
                              @RequestParam Integer quantity,
                              @RequestParam MultipartFile image,
                              @RequestParam Long category,
                              @RequestParam Long manufacturer){
        this.productService.save(name, image, price, quantity, category, manufacturer);
        return "redirect:/products";
    }
}
