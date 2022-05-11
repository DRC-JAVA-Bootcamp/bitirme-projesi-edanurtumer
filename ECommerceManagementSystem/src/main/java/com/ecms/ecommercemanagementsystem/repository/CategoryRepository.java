package com.ecms.ecommercemanagementsystem.repository;

import com.ecms.ecommercemanagementsystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
