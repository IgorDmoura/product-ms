package com.compasso.teste.productms.dto;

import com.compasso.teste.productms.entity.Product;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ProductDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    @NotNull
    private Double price;

    public Product toProduct(){
        Product product = new Product();
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        return product;
    }
}