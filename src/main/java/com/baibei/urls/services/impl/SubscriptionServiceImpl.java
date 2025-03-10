package com.baibei.urls.services.impl;

import com.baibei.urls.dto.core.ShowInSettingsSubscriptionDto;
import com.baibei.urls.exceptions.SubscriptionNotFoundException;
import com.baibei.urls.models.Subscription;
import com.baibei.urls.repositories.SubscriptionRepository;
import com.baibei.urls.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription findById(long id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public List<Subscription> findByUserId(long userId) {
        return subscriptionRepository.findAllByUserId(userId);
    }

    @Override
    public boolean existsById(long id) {
        return subscriptionRepository.existsById(id);
    }

    @Override
    public boolean existsByUserId(long userId) {
        return subscriptionRepository.existsByUserId(userId);
    }

    @Override
    public ShowInSettingsSubscriptionDto getShowInSettingsSubscriptionByUserId(long userId) {
        Subscription subscription = subscriptionRepository.findLastByUserId(userId)
                .orElse(null);

        if (subscription == null) {
            return null;
        }

        ShowInSettingsSubscriptionDto dto = new ShowInSettingsSubscriptionDto();
        dto.setExpiredDate(subscription.getExpiryDate());

        return dto;
    }

    @Override
    public ShowInSettingsSubscriptionDto getShowInSettingsSubscriptionByUserUsername(String username) {
        Subscription subscription = subscriptionRepository.findLastByUserUsername(username)
                .orElse(null);

        if (subscription == null) {
            return null;
        }

        ShowInSettingsSubscriptionDto dto = new ShowInSettingsSubscriptionDto();
        dto.setExpiredDate(subscription.getExpiryDate());

        return dto;
    }
}
