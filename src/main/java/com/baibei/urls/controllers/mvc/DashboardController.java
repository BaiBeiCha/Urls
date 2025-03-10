package com.baibei.urls.controllers.mvc;

import com.baibei.urls.models.UserInfo;
import com.baibei.urls.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/links")
public class DashboardController {

    private final UserInfoService userInfoService;

    @Value("${urls.base}")
    private String baseUrl;

    @GetMapping
    public String dashboard(Model model, @AuthenticationPrincipal UserInfo userInfo) {
        UserInfo fullUser = userInfoService.getUserWithLinks(userInfo.getId());
        model.addAttribute("links", fullUser.getMagicURLs());
        model.addAttribute("baseUrl", baseUrl);
        return "links";
    }
}
