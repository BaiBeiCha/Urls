package com.baibei.urls.repositories;

import com.baibei.urls.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findById(long id);
    List<Subscription> findAllByUserId(long userId);
    List<Subscription> findAll();
    boolean existsById(long id);
    boolean existsByUserId(long userId);
    Optional<Subscription> findLastByUserId(long userId);
    Optional<Subscription> findLastByUserUsername(String username);

}
