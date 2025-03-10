package com.baibei.urls.services.impl;

import com.baibei.urls.models.MagicURL;
import com.baibei.urls.models.UserInfo;
import com.baibei.urls.repositories.UserInfoRepository;
import com.baibei.urls.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void addLinkToUser(Long userId, MagicURL magicURL) {
        UserInfo user = userInfoRepository.findByIdWithLinks(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.hasSubscription() && user.getLinks() <= 0) {
            throw new IllegalStateException("Лимит ссылок исчерпан");
        }

        user.getMagicURLs().add(magicURL);

        if (!user.hasSubscription()) {
            user.setLinks(user.getLinks() - 1);
        }

        userInfoRepository.save(user);
        updateSecurityContext(user);
    }

    @Override
    public void updateSecurityContext(UserInfo updatedUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserInfo newUserDetails = userInfoRepository.findByIdWithLinks(updatedUser.getId()).get();

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                newUserDetails,
                auth.getCredentials(),
                auth.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userInfoRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userInfoRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByUsernameOrEmail(String username, String email) {
        return userInfoRepository.existsByUsernameOrEmail(username, email);
    }

    @Override
    public List<UserInfo> findAll() {
        return userInfoRepository.findAll();
    }

    @Override
    public List<UserInfo> findAll(Integer from, Integer to) {
        if (from == null && to == null) {
            return userInfoRepository.findAll();
        } else if (from == null) {
            List<UserInfo> urls = userInfoRepository.findAll();
            return urls.subList(0, to);
        } else if (to == null) {
            List<UserInfo> urls = userInfoRepository.findAll();
            return urls.subList(0, from);
        } else {
            List<UserInfo> urls = userInfoRepository.findAll();
            return urls.subList(from, to);
        }
    }

    @Override
    public UserInfo find(long id) {
        return userInfoRepository.findById(id).orElse(null);
    }

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserInfo findByUsernameOrEmail(String username, String email) {
        return userInfoRepository.findByUsernameOrEmail(username, email).orElse(null);
    }

    @Override
    public boolean checkPassword(UserInfo user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean checkPassword(long id, String password) {
        return checkPassword(find(id), password);
    }

    @Override
    public UserInfo saveWithNewPassword(UserInfo userInfo, String password) {
        userInfo.setPassword(passwordEncoder.encode(password));
        return userInfoRepository.save(userInfo);
    }

    @Transactional(readOnly = true)
    @Override
    public UserInfo getUserWithLinks(Long userId) {
        return userInfoRepository.findByIdWithLinks(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
