package mk.ukim.finki.wp.eshop.web;

import mk.ukim.finki.wp.eshop.model.Category;
import mk.ukim.finki.wp.eshop.model.Manufacturer;
import mk.ukim.finki.wp.eshop.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategoriesPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("categories", categoryService.listCategories());
        model.addAttribute("bodyContent", "categories");
        return "master-template";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCategoryPage(Model model){
        model.addAttribute("bodyContent", "add-category");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveCategory(@RequestParam String name, @RequestParam String description){
        this.categoryService.create(name, description);
        return "redirect:/categories";
    }
}
