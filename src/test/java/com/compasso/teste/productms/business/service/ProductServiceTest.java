package com.compasso.teste.productms.business.service;

import com.compasso.teste.productms.dto.ProductDTO;
import com.compasso.teste.productms.entity.Product;
import com.compasso.teste.productms.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepositoryMock;

    @Test
    @DisplayName("[PRODUCT] - Should successfully create product")
    void saveProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Product");
        productDTO.setDescription("The best product");
        productDTO.setPrice(500.99);

        Product expectedProduct = new Product();
        expectedProduct.setName("Product");
        expectedProduct.setDescription("The best product");
        expectedProduct.setPrice(500.99);

        Product product = productService.createProduct(productDTO);

        Mockito.verify(productRepositoryMock).save(expectedProduct);

    }

    @Test
    @DisplayName("[PRODUCT] - Should update existing product")
    void shouldUpdateExistingProductSuccessfully() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Product");
        productDTO.setDescription("The best product");
        productDTO.setPrice(500.99);

        Product outdatedProduct = new Product();
        outdatedProduct.setId(1L);
        outdatedProduct.setName("Product");
        outdatedProduct.setDescription("Old Product");
        outdatedProduct.setPrice(100.50);

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product");
        expectedProduct.setDescription("The best product");
        expectedProduct.setPrice(500.99);

        BDDMockito.when(productRepositoryMock.findById(1L)).thenReturn(Optional.of(outdatedProduct));

        productService.updateProduct(1L, productDTO);

        Mockito.verify(productRepositoryMock).save(expectedProduct);
    }


}