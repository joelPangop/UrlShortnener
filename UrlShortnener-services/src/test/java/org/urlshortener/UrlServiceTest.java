package org.urlshortener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.urlshortener.models.UrlShortenerEntity;
import org.urlshortener.repositories.UrlRepository;
import org.urlshortener.services.UrlService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@SpringBootTest
public class UrlServiceTest {

    @Autowired
    private UrlRepository repo;

    @Autowired
    private UrlService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(UrlRepository.class);
    }

    @Test
    void sameUrlReturnsSameCode() {
        String url = "https://Example.com:443/path?a=1";

        //Teste pour simuler le cas ou à partir de la fonction findByNormalizedUrl on ne trouve pas d'url raccourci avec de l'url passé en paramètre
        Mockito.when(repo.findByNormalizedUrl(any())).thenReturn(Optional.empty());

        //Simule que le code court n’existe pas encore
        Mockito.when(repo.findById(any())).thenReturn(Optional.empty());

        //Simule la sauvegarde dans la bd et rerourne le même objet
        Mockito.when(repo.saveAndFlush(any())).thenAnswer(inv -> inv.getArgument(0));

        //On simule 2 raccourcissements d'une mème url
        UrlShortenerEntity m1 = service.shorten(url);
        UrlShortenerEntity m2 = service.shorten(url);

        //On s'assure que les 2 raccourcissements sont identiques
        assertThat(m1.getCode()).isEqualTo(m2.getCode());
    }

    @Test
    void unknownCodeThrows() {
        //Tester que le service qui recherche un code erroné dans la bd lève une exception IllegalArgumentException
        Mockito.when(repo.findById("abc")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.resolve("abc"));
    }
}