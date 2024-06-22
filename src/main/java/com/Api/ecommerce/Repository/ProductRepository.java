package com.Api.ecommerce.Repository;

import com.Api.ecommerce.Model.Entity.Category;
import com.Api.ecommerce.Model.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}