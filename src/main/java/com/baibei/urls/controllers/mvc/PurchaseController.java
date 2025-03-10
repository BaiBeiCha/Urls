package com.baibei.urls.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PurchaseController {

    @GetMapping("/links/{id}/purchase-ads-removal")
    public String linksAdsRemoval(@PathVariable int id) {
        return "purchase-ads-removal";
    }
}
