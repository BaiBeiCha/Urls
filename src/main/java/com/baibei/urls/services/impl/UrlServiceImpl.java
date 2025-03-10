package com.baibei.urls.services.impl;

import com.baibei.urls.components.Base62;
import com.baibei.urls.models.MagicURL;
import com.baibei.urls.models.URL;
import com.baibei.urls.repositories.MagicURLRepository;
import com.baibei.urls.repositories.URLRepository;
import com.baibei.urls.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UrlServiceImpl implements UrlService {

    private static int SHORT_ID_LENGTH = 6;
    private static final int MAX_ATTEMPTS = 10;

    private final URLRepository urlRepository;
    private final MagicURLRepository magicURLRepository;

    private final Base62 base62;
    private final Random random = new Random();

    @Override
    public List<URL> findAll() {
        return urlRepository.findAll();
    }

    @Override
    public List<URL> findAll(Integer from, Integer to) {
        if (from == null && to == null) {
            return urlRepository.findAll();
        } else if (from == null) {
            List<URL> urls = urlRepository.findAll();
            return urls.subList(0, to);
        } else if (to == null) {
            List<URL> urls = urlRepository.findAll();
            return urls.subList(0, from);
        } else {
            List<URL> urls = urlRepository.findAll();
            return urls.subList(from, to);
        }
    }

    @Override
    public URL find(long id) {
        return urlRepository.findById(id);
    }

    @Override
    public URL save(URL url) {
        return urlRepository.save(url);
    }

    @Override
    public URL make(String rawUrl) {
        return urlRepository.findByOriginalUrl(rawUrl).orElseGet(() -> {
            String shortId = generateUniqueShortId();
            URL newUrl = new URL(rawUrl, shortId);
            return urlRepository.save(newUrl);
        });
    }

    private String generateUniqueShortId() {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            long randomNumber = generateRandomNumber();
            String shortId = base62.encode(randomNumber);

            if (!urlRepository.existsByShortId(shortId)) {
                return shortId;
            }
        }

        SHORT_ID_LENGTH++;
        return generateUniqueShortId();
    }

    private long generateRandomNumber() {
        long min = (long) Math.pow(62, UrlServiceImpl.SHORT_ID_LENGTH - 1);
        long max = (long) Math.pow(62, UrlServiceImpl.SHORT_ID_LENGTH) - 1;
        return min + Math.abs(random.nextLong()) % (max - min + 1);
    }

    @Override
    public String getFullUrl(String shortId) {
        MagicURL mUrl = magicURLRepository.findByShortId(shortId).orElse(null);
        if (mUrl != null) {
            mUrl.setClicks(mUrl.getClicks() + 1);
            magicURLRepository.save(mUrl);
            return mUrl.getOriginalUrl();
        }

        URL url = urlRepository.findByShortId(shortId).orElse(null);
        if (url == null) {
            return "URL not found";
        } else {
            return url.getOriginalUrl();
        }
    }
}
