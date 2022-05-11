package com.ecms.ecommercemanagementsystem.controller;

import com.ecms.ecommercemanagementsystem.common.ApiResponse;
import com.ecms.ecommercemanagementsystem.dto.ProductDto;
import com.ecms.ecommercemanagementsystem.model.Product;
import com.ecms.ecommercemanagementsystem.model.User;
import com.ecms.ecommercemanagementsystem.model.RequestList;
import com.ecms.ecommercemanagementsystem.service.AuthenticationService;
import com.ecms.ecommercemanagementsystem.service.RequestListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/requestlist")
public class RequestListController {

    @Autowired
    RequestListService requestListService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product,
                                                     @RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        RequestList requestList = new RequestList(user, product);

        requestListService.createWishlist(requestList);

        ApiResponse apiResponse = new ApiResponse(true, "İstek listesine ekle.");

        return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getRequestList(@PathVariable("token") String token) {

        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        List<ProductDto> productDtos = requestListService.getRequestListForUser(user);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);

    }

}
