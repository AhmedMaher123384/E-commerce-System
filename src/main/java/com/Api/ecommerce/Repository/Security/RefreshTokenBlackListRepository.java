package com.Api.ecommerce.Repository.Security;

import com.Api.ecommerce.Model.Entity.Security.RefreshTokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenBlackListRepository extends JpaRepository<RefreshTokenBlackList,Long> {
    Optional<RefreshTokenBlackList> findByToken(String token);

}
