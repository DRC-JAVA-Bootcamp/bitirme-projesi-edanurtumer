package com.ecms.ecommercemanagementsystem.controller;

import com.ecms.ecommercemanagementsystem.dto.ResponseDto;
import com.ecms.ecommercemanagementsystem.dto.user.SignInDto;
import com.ecms.ecommercemanagementsystem.dto.user.SignInResponseDto;
import com.ecms.ecommercemanagementsystem.dto.user.SignUpDto;
import com.ecms.ecommercemanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseDto signUp(@RequestBody SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        return userService.signIn(signInDto);
    }

}

