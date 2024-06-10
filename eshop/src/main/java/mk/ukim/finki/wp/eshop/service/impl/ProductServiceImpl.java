package mk.ukim.finki.wp.eshop.service.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.eshop.model.Category;
import mk.ukim.finki.wp.eshop.model.Manufacturer;
import mk.ukim.finki.wp.eshop.model.Product;
import mk.ukim.finki.wp.eshop.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wp.eshop.model.exceptions.ManufacturerNotFoundException;
import mk.ukim.finki.wp.eshop.repository.CategoryRepository;
import mk.ukim.finki.wp.eshop.repository.ManufacturerRepository;
import mk.ukim.finki.wp.eshop.repository.ProductRepository;
import mk.ukim.finki.wp.eshop.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;


import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<Product> save(String name, MultipartFile image, Double price, Integer quantity, Long categoryId, Long manufacturerId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));

        this.productRepository.deleteByName(name);

        String imageName = image.getOriginalFilename();
        Path path = Paths.get(uploadPath, imageName);
        try {
            Files.copy(image.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }

        return Optional.of(this.productRepository.save(new Product(name, "/uploads/"+imageName, price, quantity, category, manufacturer)));
    }

    @Override
    public void delete(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public List<Product> findTop3Products() {
        return productRepository.findAll().stream().sorted(Comparator.comparing(Product::getPrice, Comparator.reverseOrder())).limit(3).collect(Collectors.toList());
    }
}
