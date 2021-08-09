package com.compasso.teste.productms.business.service;

import com.compasso.teste.productms.business.exception.ProductNotFoundException;
import com.compasso.teste.productms.dto.ProductDTO;
import com.compasso.teste.productms.entity.Product;
import com.compasso.teste.productms.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductDTO productDTO){
        Product product = productDTO.toProduct();
        return productRepository.save(product);

    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = findById(id);

        BeanUtils.copyProperties(productDTO,product,"id");

        return productRepository.save(product);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteById(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    public List<Product> queryByParams(String q, Double minPrice, Double maxPrice){
        return productRepository.queryByParams(q,minPrice,maxPrice);
    }
}