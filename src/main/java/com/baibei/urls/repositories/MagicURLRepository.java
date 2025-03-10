package com.baibei.urls.repositories;

import com.baibei.urls.models.MagicURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MagicURLRepository extends JpaRepository<MagicURL, Long> {

    Optional<MagicURL> findByShortId(String shortId);
    boolean existsByShortId(String shortId);

}
