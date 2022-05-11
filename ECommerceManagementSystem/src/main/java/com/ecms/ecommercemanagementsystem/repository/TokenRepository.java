package com.ecms.ecommercemanagementsystem.repository;

import com.ecms.ecommercemanagementsystem.model.AuthenticationToken;
import com.ecms.ecommercemanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);
}
