package com.baibei.urls.controllers.mvc;

import com.baibei.urls.dto.core.UserRegisterDto;
import com.baibei.urls.models.UserInfo;
import com.baibei.urls.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserInfoService userInfoService;

    @Value("${urls.free-links-max-count}")
    private long freeLinksMaxCount;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String errorParam) {
        if (errorParam != null && !errorParam.isEmpty()) {
            model.addAttribute("status", "Username or password is incorrect!");
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(UserRegisterDto userRegisterDto, RedirectAttributes redirectAttributes) {
        if (userInfoService.existsByUsername(userRegisterDto.getUsername())) {
            redirectAttributes.addFlashAttribute("status", "Username is already in use!");
            return "redirect:/register";
        }

        if (!checkPasswords(userRegisterDto)) {
            redirectAttributes.addFlashAttribute("status", "Passwords do not match!");
            return "redirect:/register";
        }

        UserInfo user = new UserInfo();
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setEmail(userRegisterDto.getEmail());
        user.setLinks(freeLinksMaxCount);
        userInfoService.save(user);

        return "redirect:/login";
    }

    private boolean checkPasswords(UserRegisterDto userRegisterDto) {
        return userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword());
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}
