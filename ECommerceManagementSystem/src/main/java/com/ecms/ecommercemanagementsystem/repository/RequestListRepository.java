package com.ecms.ecommercemanagementsystem.repository;

import com.ecms.ecommercemanagementsystem.model.User;
import com.ecms.ecommercemanagementsystem.model.RequestList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface RequestListRepository extends JpaRepository<RequestList, Integer> {
    List<RequestList> findAllByUserOrderByCreatedDateDesc(User user);

}