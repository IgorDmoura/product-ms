package com.compasso.teste.productms.business.exception;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1603397321242124592L;

    private final Long productId;

    public ProductNotFoundException(Long id) {
        this.productId = id;
    }
}