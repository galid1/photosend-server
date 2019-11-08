package com.photosend.photosendserver01.web.admin.presentation;

import com.photosend.photosendserver01.web.admin.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminProductController {
    @Autowired
    private AdminProductService adminProductService;

    @GetMapping("/admin/{usersId}/products")
    public String inputProductInformation(@PathVariable("usersId") long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("uploadedProductList", adminProductService.getUploadedProductList());
        return "admin";
    }
}
