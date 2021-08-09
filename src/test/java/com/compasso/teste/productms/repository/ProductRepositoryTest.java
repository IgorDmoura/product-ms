package com.compasso.teste.productms.repository;


import com.compasso.teste.productms.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("JpaRepository Test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("[PRODUCT] - Should successfully create product")
    void shouldReturnAllPropertiesAfterCreatingProduct(){
        Product product = new Product(1L,"Product","Best Product",300.50);
        Product savedProduct = productRepository.save(product);

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).isNotNull();
        Assertions.assertThat(savedProduct.getName()).isEqualTo(product.getName());
        Assertions.assertThat(savedProduct.getDescription()).isEqualTo(product.getDescription());
        Assertions.assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
    }
}