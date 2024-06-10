package mk.ukim.finki.wp.eshop.service.impl;

import mk.ukim.finki.wp.eshop.model.Category;
import mk.ukim.finki.wp.eshop.repository.CategoryRepository;
import mk.ukim.finki.wp.eshop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(String name, String description) {
        if(name == null || description == null || name.isEmpty() || description.isEmpty()){
            throw new IllegalArgumentException();
        }
        Category c = new Category(name, description);
        this.categoryRepository.save(c);
        return c;
    }

    @Override
    public Category update(String name, String description) {
        if(name == null || description == null || name.isEmpty() || description.isEmpty()){
            throw new IllegalArgumentException();
        }
        Category c = new Category(name, description);
        this.categoryRepository.save(c);
        return c;
    }

    @Override
    public void delete(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.categoryRepository.deleteByName(name);
    }

    @Override
    public List<Category> listCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public List<Category> searchCategories(String searchText) {
        return this.categoryRepository.findAllByNameLike(searchText);
    }
}
