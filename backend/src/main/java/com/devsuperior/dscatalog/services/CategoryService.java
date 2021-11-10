package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = false)
    public List<CategoryDTO> findAll(){
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream().map(cat -> new CategoryDTO(cat )).collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    public CategoryDTO findOne(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO save(CategoryDTO dto){
        Category category = new Category();
        category.setName(dto.getName());
        category = categoryRepository.save(category);

        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(CategoryDTO categoryDTO){
        Category category = categoryRepository.findById(categoryDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        category.setName(categoryDTO.getName());
        category = categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    public void delete(Long id){

        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        categoryRepository.deleteById(id);
    }

}
