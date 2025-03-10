package com.baibei.urls.services.impl;

import com.baibei.urls.models.MagicURL;
import com.baibei.urls.models.UserInfo;
import com.baibei.urls.repositories.MagicURLRepository;
import com.baibei.urls.services.MagicUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class MagicUrlServiceImpl implements MagicUrlService {

    private final MagicURLRepository magicURLRepository;

    @Transactional
    @Override
    public MagicURL createLink(UserInfo user, String originalUrl, String shortId) {
        if (magicURLRepository.existsByShortId(shortId)) {
            throw new IllegalStateException("Ссылка уже существует");
        }

        MagicURL magicURL = new MagicURL();
        magicURL.setOriginalUrl(originalUrl);
        magicURL.setShortId(shortId);
        magicURL.setCreatedAt(Date.valueOf(LocalDate.now()));
        magicURL.setUserInfo(user);
        magicURL.setNoAds(false);

        return magicURLRepository.save(magicURL);
    }

    @Override
    public MagicURL findById(long id) {
        return magicURLRepository.findById(id).orElse(null);
    }

    @Override
    public MagicURL save(MagicURL magicUrl) {
        return magicURLRepository.save(magicUrl);
    }

    @Transactional
    @Override
    public void delete(MagicURL magicUrl) {
        magicURLRepository.delete(magicUrl);
    }

    @Override
    public boolean exists(String shortId) {
        return magicURLRepository.existsByShortId(shortId);
    }
}
