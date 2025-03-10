package com.baibei.urls.controllers.mvc;

import com.baibei.urls.models.URL;
import com.baibei.urls.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final UrlService urlService;

    @Value("${urls.base}")
    private String shortUrlBase;

    @GetMapping("/home")
    public String index() {
        return "index";
    }

    @PostMapping("/home")
    public String shorter(String rawUrl, Model model) {
        URL url = urlService.make(rawUrl);
        model.addAttribute("url", rawUrl);
        model.addAttribute("shorter", shortUrlBase+ "/" + url.getShortId());
        return "index";
    }
}
