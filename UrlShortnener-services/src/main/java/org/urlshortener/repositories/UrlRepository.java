package org.urlshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urlshortener.models.UrlShortenerEntity;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlShortenerEntity, String> {
    Optional<UrlShortenerEntity> findByNormalizedUrl(String normalizedUrl);
}