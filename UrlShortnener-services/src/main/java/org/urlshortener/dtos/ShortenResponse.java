package org.urlshortener.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ShortenResponse {
    private String code;

    private String shortUrl;

    private String originalUrl;
}