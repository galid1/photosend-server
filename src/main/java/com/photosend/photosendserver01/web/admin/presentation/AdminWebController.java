package com.photosend.photosendserver01.web.admin.presentation;

import com.photosend.photosendserver01.web.admin.service.AdminOrderService;
import com.photosend.photosendserver01.web.admin.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminWebController {
    private final AdminProductService adminProductService;
    private final AdminOrderService adminOrderService;

    @GetMapping("/")
    public String getAdminIndex() {
        return "admin_index";
    }

    @GetMapping("/admin/orders")
    public String getOrderList(HttpServletRequest request, Model model) {
        model.addAttribute("path", getPath(request));
        model.addAttribute("orderListGroupingByUser", adminOrderService.getOrderListGroupingByUser());
        return "admin_orders";
    }

    @GetMapping("/admin/products")
    public String getInputProductInformationPage(HttpServletRequest request, Model model) {
        model.addAttribute("path", getPath(request));
        model.addAttribute("productListGroupByUploaderId", adminProductService.getUploadedProductListGroupByUploaderId());

        return "admin_products";
    }

    private String getPath(HttpServletRequest request) {
        List<String> paths = Arrays.asList(request.getServletPath().split("/"));
        return paths.get(paths.size() - 1);
    }
}
