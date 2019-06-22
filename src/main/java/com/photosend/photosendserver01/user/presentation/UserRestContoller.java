package com.photosend.photosendserver01.user.presentation;

import com.photosend.photosendserver01.user.domain.UserInformation;
import com.photosend.photosendserver01.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserRestContoller {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public Long registerUser(@RequestBody UserInformation userInformation) {
        return userService.registerUser(userInformation);
    }

    @PostMapping("/{users-id}/tickets")
    public String uploadTickets(@PathVariable("users-id") Long userId, @RequestParam("file") MultipartFile ticketsFile) {
        String imageUrl = userService.uploadTicketImage(userId, ticketsFile);
        return imageUrl;
    }

}
