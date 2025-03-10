package com.baibei.urls.exceptions;

public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(long id) {
        super("Subscription with id " + id + " not found");
    }

    public SubscriptionNotFoundException(String username) {
        super("Subscription with username " + username + " not found");
    }
}
