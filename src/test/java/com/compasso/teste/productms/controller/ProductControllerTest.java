package com.compasso.teste.productms.controller;

import com.compasso.teste.productms.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {

    private static final Long NON_EXISTING_PRODUCT_ID = 50L;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("[PRODUCT] - Should successfully create product")
    @ParameterizedTest
    @MethodSource("provideValidProducts")
    void shouldReturnAllPropertiesAfterCreatingProduct(Long expectedIdAfterCreation, ProductDTO productDTO) throws Exception {

        String json = new ObjectMapper().writeValueAsString(productDTO);

        this.mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedIdAfterCreation));
    }

    @Test
    @DisplayName("[PRODUCT] - Name is required")
    void shouldValidateNameParam() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setDescription("Product Description Yay!");
        productDTO.setPrice(200.29);

        String json = new ObjectMapper().writeValueAsString(productDTO);

        this.mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[PRODUCT] - Description is required")
    void descriptionRequired() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("My Beautiful Product");
        productDTO.setPrice(200.29);

        String json = new ObjectMapper().writeValueAsString(productDTO);

        this.mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[PRODUCT] - Price is required")
    void priceIsRequired() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("My Beautiful Product");
        productDTO.setDescription("Product Description Yay!");

        String json = new ObjectMapper().writeValueAsString(productDTO);

        this.mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[PRODUCT] - Price must be positive")
    void priceMustBePositive() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("My Beautiful Product");
        productDTO.setDescription("Product Description Yay!");
        productDTO.setPrice(-315.00);

        String json = new ObjectMapper().writeValueAsString(productDTO);

        this.mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[PRODUCT] - Cant update non existing product")
    void cantUpdateNonExistingProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("My Updated Product");
        productDTO.setDescription("Updated Description Yo!");
        productDTO.setPrice(250.29);

        String json = new ObjectMapper().writeValueAsString(productDTO);

        String expectedMessage = String.format("No product found with Id: %s", NON_EXISTING_PRODUCT_ID);
        int expectedStatusCode = HttpStatus.NOT_FOUND.value();

        this.mockMvc.perform(
                        put("/products/" + NON_EXISTING_PRODUCT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.status_code").value(expectedStatusCode));
    }

    @Test
    @DisplayName("[PRODUCT] - Should query product by min price successfully")
    void queryByName() throws Exception {
        String query = "My Beautiful Product";

        this.mockMvc.perform(
                        get("/products/search")
                                .param("q", query))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    private static Stream<Arguments> provideValidProducts() {
        return Stream.of(
                Arguments.of(1L, validProducts().get(0)),
                Arguments.of(2L, validProducts().get(1)),
                Arguments.of(3L, validProducts().get(2))
        );
    }

    private static List<ProductDTO> validProducts() {
        ProductDTO beautifulProduct = new ProductDTO();
        beautifulProduct.setName("My Beautiful Product");
        beautifulProduct.setDescription("Product Description Yay!");
        beautifulProduct.setPrice(200.29);

        ProductDTO uglyProduct = new ProductDTO();
        uglyProduct.setName("Ugly Product");
        uglyProduct.setDescription("I just dont want this one!");
        uglyProduct.setPrice(115.20);

        ProductDTO expensiveProduct = new ProductDTO();
        expensiveProduct.setName("Expensive Product");
        expensiveProduct.setDescription("This is very expensive!");
        expensiveProduct.setPrice(15_000_000.20);

        return List.of(beautifulProduct, uglyProduct, expensiveProduct);
    }

}


