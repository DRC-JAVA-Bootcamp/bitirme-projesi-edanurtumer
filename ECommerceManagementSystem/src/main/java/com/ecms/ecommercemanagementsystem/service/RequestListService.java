package com.ecms.ecommercemanagementsystem.service;

import com.ecms.ecommercemanagementsystem.dto.ProductDto;
import com.ecms.ecommercemanagementsystem.model.User;
import com.ecms.ecommercemanagementsystem.model.RequestList;
import com.ecms.ecommercemanagementsystem.repository.RequestListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class RequestListService {
    @Autowired
    RequestListRepository requestListRepository;
    @Autowired
    ProductService productService;
    public void createWishlist(RequestList requestList) {
        requestListRepository.save(requestList);
    }
    public List<ProductDto> getRequestListForUser(User user) {
        final List<RequestList> wishLists = requestListRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for (RequestList wishList: wishLists) {
            productDtos.add(productService.getProductDto(wishList.getProduct()));
        }
        return productDtos;
    }

}
