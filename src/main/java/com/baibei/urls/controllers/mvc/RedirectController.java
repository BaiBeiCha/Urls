package com.baibei.urls.controllers.mvc;

import com.baibei.urls.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class RedirectController {

    private final UrlService urlService;

    @Value("${urls.link-redirect-time}")
    private long linkRedirectTime;

    @GetMapping("/{shortId}")
    public String redirectToShortUrl(@PathVariable String shortId, Model model) {
        String fullUrl = urlService.getFullUrl(shortId);
        if (fullUrl != null && !fullUrl.isEmpty()) {
            model.addAttribute("targetUrl", fullUrl);
            model.addAttribute("waitTime", linkRedirectTime);
            return "redirect";
        }
        return "redirect:/error/404";
    }
}
