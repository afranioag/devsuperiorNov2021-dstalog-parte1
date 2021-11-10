package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll(){
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream().map(cat -> new CategoryDTO(cat )).collect(Collectors.toList());
    }

    public CategoryDTO findOne(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        return new CategoryDTO(category);
    }

    public void save(String name){
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }

    public Category update(Long id, String name){
        Category category = categoryRepository.findById(id).get();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }

}
