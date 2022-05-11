package com.ecms.ecommercemanagementsystem.service;

import com.ecms.ecommercemanagementsystem.dto.ResponseDto;
import com.ecms.ecommercemanagementsystem.dto.user.SignInDto;
import com.ecms.ecommercemanagementsystem.dto.user.SignInResponseDto;
import com.ecms.ecommercemanagementsystem.dto.user.SignUpDto;
import com.ecms.ecommercemanagementsystem.exceptions.AuthenticationFailException;
import com.ecms.ecommercemanagementsystem.exceptions.CustomException;
import com.ecms.ecommercemanagementsystem.model.AuthenticationToken;
import com.ecms.ecommercemanagementsystem.model.User;
import com.ecms.ecommercemanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationService authenticationService;
    @Transactional
    public ResponseDto signUp(SignUpDto signupDto) {
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            throw new CustomException("Kullanıcı zaten mevcut.");
        }

        String encryptedPassword = signupDto.getPassword();

        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(),
                signupDto.getEmail(), encryptedPassword);

        userRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "Kullanıcı başarıyla oluşturuldu.");
        return responseDto;
    }
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }
    public SignInResponseDto signIn(SignInDto signInDto) {

        User user = userRepository.findByEmail(signInDto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("Kullanıcı geçerli değil.");
        }

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException("Yanlış şifre.");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if (Objects.isNull(token)) {
            throw new CustomException("Mevcut değil.");
        }

        return new SignInResponseDto("success", token.getToken());

    }

}

