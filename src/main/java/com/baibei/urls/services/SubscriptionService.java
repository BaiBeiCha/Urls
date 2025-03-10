package com.baibei.urls.services;

import com.baibei.urls.dto.core.ShowInSettingsSubscriptionDto;
import com.baibei.urls.models.Subscription;

import java.util.List;

public interface SubscriptionService {

    Subscription findById(long id);
    List<Subscription> findAll();
    List<Subscription> findByUserId(long userId);
    boolean existsById(long id);
    boolean existsByUserId(long userId);
    ShowInSettingsSubscriptionDto getShowInSettingsSubscriptionByUserId(long userId);
    ShowInSettingsSubscriptionDto getShowInSettingsSubscriptionByUserUsername(String username);

}
