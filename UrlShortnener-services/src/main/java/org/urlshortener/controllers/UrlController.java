package org.urlshortener.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urlshortener.dtos.ShortenRequest;
import org.urlshortener.dtos.ShortenResponse;
import org.urlshortener.models.UrlShortenerEntity;
import org.urlshortener.services.UrlService;

@RestController
@RequiredArgsConstructor
public class UrlController {

    @Autowired
    private UrlService service;

    // 1) Appel rest pour Raccourcir l'url
    @PostMapping(path = "/api/shorten", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortenResponse> shorten(@Valid @RequestBody ShortenRequest req) {
        UrlShortenerEntity mapping = service.shorten(req.getUrl());
        return ResponseEntity.ok(new ShortenResponse(
                mapping.getCode(),
                service.buildShortUrl(mapping.getCode()),
                mapping.getOriginalUrl()
        ));
    }

    // 2) Étendre (retourne l'URL originale en texte) — pas de redirection
    @GetMapping("/api/expand/{code}")
    public ResponseEntity<String> expand(@PathVariable String code) {
        UrlShortenerEntity mapping = service.resolve(code);
        return ResponseEntity.ok(mapping.getOriginalUrl());
    }

    // 3) Option polyvalente sur l'URL courte : dans le casd ou on veut directement reccupérer l'url original à partir du raccourcie fourni
    @GetMapping("/{code}")
    public ResponseEntity<?> shortLink(@PathVariable String code) {
        UrlShortenerEntity mapping = service.resolve(code);
            return ResponseEntity.ok(mapping.getOriginalUrl());
    }
}
