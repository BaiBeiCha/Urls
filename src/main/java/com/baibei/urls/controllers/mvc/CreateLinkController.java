package com.baibei.urls.controllers.mvc;

import com.baibei.urls.dto.core.UpdateMagicLinkDto;
import com.baibei.urls.models.MagicURL;
import com.baibei.urls.models.UserInfo;
import com.baibei.urls.services.MagicUrlService;
import com.baibei.urls.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
@RequestMapping("/links")
public class CreateLinkController {

    private final MagicUrlService magicUrlService;
    private final UserInfoService userInfoService;

    @Value("${urls.base}")
    private String urlBase;

    @GetMapping("/new")
    public String newLink(Model model, @AuthenticationPrincipal UserInfo userInfo) {
        model.addAttribute("base", urlBase);
        model.addAttribute("remainingFreeLinks", userInfo.getLinks());
        model.addAttribute("hasSubscription", userInfo.hasSubscription());
        return "link-create";
    }

    @PostMapping
    @Transactional
    public String createLink(
            RedirectAttributes redirectAttributes,
            UpdateMagicLinkDto link,
            @AuthenticationPrincipal UserInfo userInfo) {
        if (link.getShortId() == null || link.getShortId().isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Короткая ссылка не может быть пустой"
            );
        }

        try {
            MagicURL magicURL = magicUrlService.createLink(
                    userInfo,
                    link.getOriginalUrl(),
                    link.getShortId()
            );

            userInfoService.addLinkToUser(userInfo.getId(), magicURL);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Ссылка успешно создана!"
            );
            return "redirect:/links";

        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/links/new";
        }
    }

    private Date getCurrentDate() {
        return Date.valueOf(LocalDate.now());
    }
}
