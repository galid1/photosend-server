package com.photosend.photosendserver01.user.presentation;

import com.photosend.photosendserver01.user.domain.UserInformation;
import com.photosend.photosendserver01.user.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    @PostMapping("/")
    public Long registerUser(@RequestBody UserInformation userInformation) {
        return userRegisterService.registerUser(userInformation);
    }
}
