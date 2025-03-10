package com.baibei.urls.services;

import com.baibei.urls.models.MagicURL;
import com.baibei.urls.models.UserInfo;
import org.springframework.transaction.annotation.Transactional;

public interface MagicUrlService {

    @Transactional
    MagicURL createLink(UserInfo user, String originalUrl, String shortId);

    MagicURL findById(long id);
    MagicURL save(MagicURL magicUrl);
    void delete(MagicURL magicUrl);
    boolean exists(String shortId);

}
