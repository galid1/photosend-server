package com.photosend.photosendserver01.domains.user.presentation;

import com.photosend.photosendserver01.domains.user.exception.UserDuplicatedException;
import com.photosend.photosendserver01.domains.user.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class UserRestControllerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(501, "USER NOT FOUND");
    }

    @ExceptionHandler(UserDuplicatedException.class)
    public void userDuplicatedException(HttpServletResponse response) throws IOException {
        response.sendError(502, "USER DUPLICATED");
    }
}
