package com.compasso.teste.productms.repository;

import com.compasso.teste.productms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p " +
            "FROM Product  p " +
            "WHERE (:q IS NULL OR (p.name = :q OR p.description = :q)) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> queryByParams(String q, Double minPrice, Double maxPrice);
}