package com.photosend.photosendserver01.domains.user.presentation;

import com.photosend.photosendserver01.domains.user.domain.user.UserInformation;
import com.photosend.photosendserver01.domains.user.service.UserInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserInformationController {

    private final UserInformationService userInformationService;

    @GetMapping("/{userId}")
    public UserInformation getUserInformation(@PathVariable("userId") long userId) {
        return userInformationService.getUserInformation(userId);
    }
}
