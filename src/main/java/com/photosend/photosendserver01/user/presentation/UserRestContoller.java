package com.photosend.photosendserver01.user.presentation;

import com.photosend.photosendserver01.user.domain.UserInformation;
import com.photosend.photosendserver01.user.domain.exception.UploadTicketException;
import com.photosend.photosendserver01.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler
    public ResponseEntity uploadTicketExceptionHandler(UploadTicketException uploadTicketException) {
        String exceptionJsonBody = "{\"message\" : \"" + uploadTicketException.getMessage() + "\" }";
        return ResponseEntity.badRequest().body(exceptionJsonBody);
    }
}
