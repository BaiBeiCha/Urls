package com.baibei.urls.controllers.api;

import com.baibei.urls.models.URL;
import com.baibei.urls.services.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/url")
public class URLsController {

    private final UrlService urlService;

    @GetMapping
    public List<URL> getURLs(@RequestParam(required = false) Integer from,
                             @RequestParam(required = false) Integer to) {
        return urlService.findAll(from, to);
    }

    @GetMapping("/{id}")
    public URL getURL(@PathVariable long id) {
        return urlService.find(id);
    }

    @PostMapping
    public URL createURL(@RequestBody URL url) {
        return urlService.save(url);
    }

    @PatchMapping
    public URL updateURL(@RequestBody URL url) {
        return urlService.save(url);
    }

}
