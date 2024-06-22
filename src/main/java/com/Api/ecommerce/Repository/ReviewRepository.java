package com.Api.ecommerce.Repository;

import com.Api.ecommerce.Model.Entity.Product;
import com.Api.ecommerce.Model.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
}

