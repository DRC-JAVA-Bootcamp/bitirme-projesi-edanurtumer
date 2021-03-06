package com.ecms.ecommercemanagementsystem.service;

import com.ecms.ecommercemanagementsystem.exceptions.AuthenticationFailException;
import com.ecms.ecommercemanagementsystem.model.AuthenticationToken;
import com.ecms.ecommercemanagementsystem.model.User;
import com.ecms.ecommercemanagementsystem.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;
@Service
public class AuthenticationService {
    @Autowired
    TokenRepository tokenRepository;
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }
    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }
    public User getUser(String token) {
        final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if(Objects.isNull(authenticationToken)) {
            return null;
        }
        return authenticationToken.getUser();
    }
    public void authenticate(String token) throws AuthenticationFailException {
        if(Objects.isNull(token)) {
            throw new AuthenticationFailException("Mevcut değil.");
        }
        if(Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("Geçerli değil.");
        }
    }

}

