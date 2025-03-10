package com.baibei.urls.controllers.api;

import com.baibei.urls.dto.api.CreateUserDto;
import com.baibei.urls.models.UserInfo;
import com.baibei.urls.services.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserInfoService userInfoService;

    @GetMapping
    public List<UserInfo> getUsers(@RequestParam(required = false) Integer from,
                                   @RequestParam(required = false) Integer to) {
        return userInfoService.findAll(from, to);
    }

    @GetMapping("/{id}")
    public UserInfo getUser(@PathVariable long id) {
        return userInfoService.find(id);
    }

    @PostMapping
    public UserInfo addUser(@RequestBody CreateUserDto createUser,
                            PasswordEncoder passwordEncoder) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(createUser.getUsername());
        userInfo.setPassword(passwordEncoder.encode(createUser.getPassword()));
        userInfo.setEmail(createUser.getEmail());
        return userInfoService.save(userInfo);
    }
}
