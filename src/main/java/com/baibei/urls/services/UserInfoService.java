package com.baibei.urls.services;

import com.baibei.urls.models.MagicURL;
import com.baibei.urls.models.UserInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserInfoService {

    @Transactional
    void addLinkToUser(Long userId, MagicURL magicURL);

    void updateSecurityContext(UserInfo updatedUser);

    UserInfo save(UserInfo userInfo);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByUsernameOrEmail(String username, String email);
    List<UserInfo> findAll();
    List<UserInfo> findAll(Integer from, Integer to);
    UserInfo find(long id);
    UserInfo findByUsername(String username);
    UserInfo findByUsernameOrEmail(String username, String email);
    boolean checkPassword(UserInfo user, String password);
    boolean checkPassword(long id, String password);
    UserInfo saveWithNewPassword(UserInfo userInfo, String password);
    public UserInfo getUserWithLinks(Long userId);

}
