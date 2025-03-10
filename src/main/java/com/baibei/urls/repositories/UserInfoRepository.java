package com.baibei.urls.repositories;

import com.baibei.urls.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByUsernameOrEmail(String username, String email);
    Optional<UserInfo> findByUsernameOrEmail(String username, String email);

    @Query("SELECT u FROM UserInfo u LEFT JOIN FETCH u.magicURLs WHERE u.id = :userId")
    Optional<UserInfo> findByIdWithLinks(@Param("userId") Long userId);
}
