package mk.ukim.finki.wp.eshop.web;

import mk.ukim.finki.wp.eshop.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getManufacturersPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("bodyContent", "manufacturers");
        return "master-template";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addManufacturerPage(Model model){
        model.addAttribute("bodyContent", "add-manufacturer");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveManufacturer(@RequestParam String name, @RequestParam String address){
        this.manufacturerService.save(name, address);
        return "redirect:/manufacturers";
    }
}
