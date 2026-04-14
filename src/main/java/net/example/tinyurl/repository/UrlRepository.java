package net.example.tinyurl.repository;

import net.example.tinyurl.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByShortCode(String shortCode);
    Optional<UrlMapping> findByOriginalUrl(String originalUrl);
    List<UrlMapping> findTop5ByOrderByClickCountDesc();
}