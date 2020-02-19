package com.photosend.photosendserver01.web.admin.presentation;

import com.photosend.photosendserver01.web.admin.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminWebController {
    @Autowired
    private AdminProductService adminProductService;

//    @GetMapping("/admin/{userId}/products")
//    public String inputProductInformation(@PathVariable("userId") long userId, Model model) {
//        model.addAttribute("userId", userId);
//        model.addAttribute("uploadedProductList", adminProductService.getUploadedProductList());
//        return "admin";
//    }

    @GetMapping("/")
    public String getAdminIndex() {
        return "admin_index";
    }

    @GetMapping("/admin/orders")
    public String getOrderList() {
        return "orders";
    }

    @GetMapping("/admin/products")
    public String inputProductInformation(Model model) {
        model.addAttribute("uploadedProductList", adminProductService.getUploadedProductList());
        return "admin_products";
    }

}
