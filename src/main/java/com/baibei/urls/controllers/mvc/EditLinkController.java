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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/links")
public class EditLinkController {

    private final MagicUrlService magicUrlService;
    private final UserInfoService userInfoService;

    @Value("${urls.base}")
    private String baseUrl;

    @Value("${urls.ads-removal-price}")
    private int adsRemovalPrice;

    @GetMapping("/{linkId}/edit")
    public String link(
            Model model,
            @PathVariable("linkId") long linkId,
            @AuthenticationPrincipal UserInfo userInfo) {
        model.addAttribute("link", magicUrlService.findById(linkId));
        model.addAttribute("removalPrice", adsRemovalPrice);
        model.addAttribute("user", userInfo);
        model.addAttribute("baseUrl", baseUrl);
        return "link-edit";
    }

    @PostMapping("/{linkId}")
    public String updateLink(
            RedirectAttributes redirectAttributes,
            @PathVariable("linkId") long linkId,
            UpdateMagicLinkDto newLink) {
        MagicURL url = magicUrlService.findById(linkId);
        url.setOriginalUrl(newLink.getOriginalUrl());
        url.setShortId(newLink.getShortId());
        System.out.println("Меняю ссылку: " + url.getOriginalUrl() + " | " + url.getShortId());
        magicUrlService.save(url);

        return "redirect:/links";
    }

    @PostMapping("/{linkId}/delete")
    @Transactional
    public String deleteLink(RedirectAttributes redirectAttributes,
                             @PathVariable("linkId") long linkId,
                             @AuthenticationPrincipal UserInfo userInfo) {

        UserInfo fullUser = userInfoService.getUserWithLinks(userInfo.getId());
        MagicURL url = magicUrlService.findById(linkId);

        if (url != null && fullUser.getMagicURLs().contains(url)) {
            magicUrlService.delete(url);
            fullUser.getMagicURLs().remove(url);
            fullUser.setLinks(fullUser.getLinks() + 1);

            userInfoService.save(fullUser);
            userInfoService.updateSecurityContext(fullUser);
        }

        return "redirect:/links";
    }
}
