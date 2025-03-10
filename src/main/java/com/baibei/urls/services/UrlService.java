package com.baibei.urls.services;

import com.baibei.urls.models.URL;

import java.util.List;

public interface UrlService {

    List<URL> findAll();
    List<URL> findAll(Integer from, Integer to);
    URL find(long id);
    URL save(URL url);
    URL make(String rawUrl);
    String getFullUrl(String shortId);

}
