package com.baibei.urls.repositories;

import com.baibei.urls.models.URL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface URLRepository extends CrudRepository<URL, Long> {

    Optional<URL> findByOriginalUrl(String url);
    Optional<URL> findByShortId(String id);
    boolean existsByOriginalUrl(String url);
    boolean existsByShortId(String id);
    URL findById(long id);
    long count();
    List<URL> findAll();

}
