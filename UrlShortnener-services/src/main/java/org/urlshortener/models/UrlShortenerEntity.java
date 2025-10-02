package org.urlshortener.models;

import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "urlshortener",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_normalized_url", columnNames = {"normalized_url"})
        }) //Contrainte d'unicité
public class UrlShortenerEntity {

    @Id
    @Column(name = "code", length = 10, nullable = false, updatable = false)
    private String code; // code court (≤10)

    @Column(name = "normalized_url", nullable = false, length = 2048)
    private String normalizedUrl; // version normalisée (unique) pour uniformiser l'url par exemple rendre la forme camelcase ou majuscule (possiblement frappé par erreur) de l'url en minuscule

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl; // telle que reçue

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}