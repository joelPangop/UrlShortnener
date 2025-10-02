package org.urlshortener.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ShortenRequest {
        @NotBlank(message = "L'URL est requise")
        @Pattern(regexp = "^(?i)(https?://).+", message = "L'URL doit commencer par http:// ou https://")
        private String url;
}
