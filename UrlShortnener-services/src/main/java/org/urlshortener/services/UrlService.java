package org.urlshortener.services;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.urlshortener.models.UrlShortenerEntity;
import org.urlshortener.repositories.UrlRepository;
import org.urlshortener.utils.SimpleHasher;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository repo;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.code-max-length:10}")
    private int codeMaxLen;

    @Transactional
    public UrlShortenerEntity shorten(String rawUrl) {
        String normalized = normalize(rawUrl);

// 1) même URL → même enregistrement
        Optional<UrlShortenerEntity> existing = repo.findByNormalizedUrl(normalized);
        if (existing.isPresent()) return existing.get();

// 2) Génération déterministe du code, gestion de collisions
        for (int salt = 0; salt < 100; salt++) {
            String code = SimpleHasher.hashHex(normalized, salt, codeMaxLen);
            Optional<UrlShortenerEntity> byId = repo.findById(code);
            if (byId.isEmpty()) {
                UrlShortenerEntity mapping = UrlShortenerEntity.builder()
                        .code(code)
                        .normalizedUrl(normalized)
                        .originalUrl(rawUrl)
                        .createdAt(Instant.now())
                        .build();
                return repo.saveAndFlush(mapping);
            } else if (byId.get().getNormalizedUrl().equals(normalized)) {
                return byId.get();
            }
        }
        throw new IllegalStateException("Impossible de générer un code unique après plusieurs essais");
    }

    @Transactional
    public UrlShortenerEntity resolve(String code) {
        UrlShortenerEntity mapping = repo.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("Code inconnu: " + code));
        return mapping;
    }

    public String buildShortUrl(String code) {
        String base = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length()-1) : baseUrl;
        return base + "/" + code;
    }

    static String normalize(String url) {
        try {
            URI uri = new URI(url.trim());
            String scheme = uri.getScheme() != null ? uri.getScheme().toLowerCase() : "http";
            String host = uri.getHost() != null ? uri.getHost().toLowerCase() : null;
            int port = uri.getPort();
            String path = StringUtils.hasText(uri.getPath()) ? uri.getPath() : "/";
            String query = uri.getQuery();

            if (("http".equals(scheme) && port == 80) || ("https".equals(scheme) && port == 443)) {
                port = -1;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(scheme).append("://");
            if (host != null) sb.append(host);
            if (port != -1) sb.append(":" ).append(port);
            sb.append(path);
            if (query != null) sb.append("?").append(query);
            return sb.toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URL invalide: " + url);
        }
    }
}