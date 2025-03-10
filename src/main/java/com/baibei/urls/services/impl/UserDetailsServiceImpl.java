package com.baibei.urls.services.impl;

import com.baibei.urls.models.UserInfo;
import com.baibei.urls.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoService.findByUsernameOrEmail(username, username);
        if (user != null) return user;
        throw new UsernameNotFoundException("User ‘" + username + "’ not found");
    }
}
