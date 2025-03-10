package com.baibei.urls.controllers.mvc;

import com.baibei.urls.dto.core.ShowInSettingsSubscriptionDto;
import com.baibei.urls.dto.core.ShowInSettingsUserInfoDto;
import com.baibei.urls.dto.core.UserPasswordDto;
import com.baibei.urls.dto.core.UserProfileInfoDto;
import com.baibei.urls.models.UserInfo;
import com.baibei.urls.services.SubscriptionService;
import com.baibei.urls.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
@RequestMapping("/settings")
public class SettingsController {

    private final UserInfoService userInfoService;
    private final SubscriptionService subscriptionService;

    @GetMapping
    public String settings(
            Model model,
            @AuthenticationPrincipal UserInfo userInfo) {
        ShowInSettingsUserInfoDto user = new ShowInSettingsUserInfoDto();
        user.setUsername(userInfo.getUsername());
        user.setEmail(userInfo.getEmail());

        ShowInSettingsSubscriptionDto showInSettingsSubscriptionByUserUsername =
                subscriptionService.getShowInSettingsSubscriptionByUserUsername(userInfo.getUsername());
        user.setShowInSettingsSubscription(showInSettingsSubscriptionByUserUsername);

        LocalDate currentDate = LocalDate.now();
        if (showInSettingsSubscriptionByUserUsername == null) {
            user.setSubscriptionActive(false);
        } else {
            Date date = showInSettingsSubscriptionByUserUsername.getExpiredDate();
            LocalDate expiredDate = date.toLocalDate();
            user.setSubscriptionActive(!expiredDate.isAfter(currentDate));
        }

        model.addAttribute("user", user);
        return "settings";
    }

    @PostMapping("/profile")
    public String profile(
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserInfo userInfo,
            UserProfileInfoDto newProfileInfo) {
        if (checkUsername(userInfo, newProfileInfo)) {
            userInfo.setUsername(newProfileInfo.getUsername());
        } else {
            redirectAttributes.addFlashAttribute(
                    "profileStatus", "Пользователь с именем "
                            + newProfileInfo.getUsername() + " уже существует"
            );
            return "redirect:/settings";
        }

        if (checkEmail(userInfo, newProfileInfo)) {
            userInfo.setEmail(newProfileInfo.getEmail());
        } else {
            redirectAttributes.addFlashAttribute(
                    "profileStatus", "Пользователь с почтой "
                            + newProfileInfo.getEmail() + " уже существует"
            );
            return "redirect:/settings";
        }

        userInfoService.save(userInfo);

        return "redirect:/settings";
    }

    private boolean checkUsername(UserInfo userInfo, UserProfileInfoDto userProfileInfoDto) {
        String oldUsername = userInfo.getUsername();
        String newUsername = userProfileInfoDto.getUsername();

        if (oldUsername.equals(newUsername)) {
            return true;
        } else {
            return !userInfoService.existsByUsername(newUsername);
        }
    }

    private boolean checkEmail(UserInfo userInfo, UserProfileInfoDto userProfileInfoDto) {
        String oldEmail = userInfo.getEmail();
        String newEmail = userProfileInfoDto.getEmail();

        if (oldEmail.equals(newEmail)) {
            return true;
        } else {
            return !userInfoService.existsByEmail(newEmail);
        }
    }

    @PostMapping("/password")
    public String password(
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserInfo userInfo,
            UserPasswordDto newPassword) {

        if (!userInfoService.checkPassword(userInfo, newPassword.getOldPassword())) {
            redirectAttributes.addFlashAttribute("passwordStatus", "Wrong password");
            return "redirect:/settings";
        }

        if (!newPassword.getPassword().equals(newPassword.getPasswordConfirm())) {
            redirectAttributes.addFlashAttribute("passwordStatus", "Passwords do not match");
            return "redirect:/settings";
        }

        userInfoService.saveWithNewPassword(userInfo, newPassword.getPassword());

        return "redirect:/settings";
    }
}
