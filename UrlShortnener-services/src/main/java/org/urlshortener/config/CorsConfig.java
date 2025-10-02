package org.urlshortener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.frontend-origin:http://localhost:3000}")
    private String frontendOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(frontendOrigin) //donne la permission de recevoir et envoyer des requètes aux urls passées en paramètre
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS") //Liste les methodes https acceptées
                .allowedHeaders("*") //Accepte toutes les en-tètes personnalisées
                .allowCredentials(false)     //  True si on envoie cookies/Authorization cross-site
                .maxAge(3600); /*Durée de vie du cache du preflight CORS en secondes. Vérifie si:
                - le domaine est autorisé (allowedOrigins)
                - les méthodes sont permises (allowedMethods)
                - les headers sont acceptés (allowedHeaders)

                On peut mémoriser la réponse du preflight pendant 3600 secondes = 1 heure
                */
    }
}