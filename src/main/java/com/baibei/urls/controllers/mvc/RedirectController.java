package com.baibei.urls.controllers.mvc;

import com.baibei.urls.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class RedirectController {

    private final UrlService urlService;

    @GetMapping("/{shortId}")
    public String redirectToShortUrl(@PathVariable String shortId) {
        String fullUrl = urlService.getFullUrl(shortId);
        if (fullUrl != null && !fullUrl.isEmpty()) {
            return "redirect:" + fullUrl;
        }
        return "redirect:/error/404";
    }
}
