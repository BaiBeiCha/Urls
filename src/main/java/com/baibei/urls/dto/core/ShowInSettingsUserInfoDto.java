package com.baibei.urls.dto.core;

import lombok.Data;

@Data
public class ShowInSettingsUserInfoDto {

    private String username;
    private String email;
    private ShowInSettingsSubscriptionDto showInSettingsSubscription;
    private Boolean subscriptionActive;

}
