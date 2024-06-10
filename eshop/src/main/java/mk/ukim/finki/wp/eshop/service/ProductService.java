package mk.ukim.finki.wp.eshop.service;

import mk.ukim.finki.wp.eshop.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    Optional<Product> save(String name, MultipartFile image, Double price, Integer quantity, Long categoryId, Long manufacturerId);
    void delete(Long id);
    List<Product> findTop3Products();
}
