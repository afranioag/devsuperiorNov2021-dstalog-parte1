package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = false)
    public Page<ProductDTO> findAllPaged(Pageable pageable){
        Page<Product> productList = productRepository.findAll(pageable);
        return productList.map(cat -> new ProductDTO(cat ));
    }

    @Transactional(readOnly = false)
    public ProductDTO findOne(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceEntityNotFoundException("Entity not found"));
        return new ProductDTO(product, product.getCategories());
    }

    @Transactional
    public ProductDTO save(ProductDTO dto){
        Product product = new Product();
        //product.setName(dto.getName());
        product = productRepository.save(product);

        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO){

        try {
            Product product = productRepository.getOne(id);
            //product.setName(productDTO.getName());
            product = productRepository.save(product);
            return new ProductDTO(product);
        }catch (EntityNotFoundException e){
            throw new ResourceEntityNotFoundException("Entity not found");
        }
    }

    public void delete(Long id){
        try {
            productRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResourceEntityNotFoundException("Entity not found");
        }
        catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrity violation ");
        }
    }

}
