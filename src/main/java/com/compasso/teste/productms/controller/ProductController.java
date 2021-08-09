package com.compasso.teste.productms.controller;

import com.compasso.teste.productms.business.service.ProductService;
import com.compasso.teste.productms.dto.ProductDTO;
import com.compasso.teste.productms.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;

    }
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO){
        log.info("M-createProduct, message-init, productDTO={}", productDTO);
        Product savedProduct = productService.createProduct(productDTO);
        log.info("M-createProduct, message=Saved product sucessfully, product={}", savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO){
        log.info("M-updateProduct, message-init, productId={},productDTO={}",id,productDTO);
        Product updatedProduct = productService.updateProduct(id,productDTO);
        log.info("M-updateProduct, message=Sucessfully update product, productId={}",id);
        return ResponseEntity.ok(updatedProduct);

    }

    @GetMapping("/products")
    public List<Product> fetchAll(){
        return productService.findAll();

    }

    @GetMapping("/products/{id}")
    public Product fetchById(@PathVariable Long id){
        return productService.findById(id);

    }

    @GetMapping("/products/search")
    public List<Product> searchProduct(
            @RequestParam(required = false) String q,
            @RequestParam(required = false, name="min_price") Double minPrice,
            @RequestParam(required = false, name = "max_price") Double maxPrice){

        return productService.queryByParams(q, minPrice, maxPrice);
    }

    @DeleteMapping("/products/{id}")
    public void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }

}