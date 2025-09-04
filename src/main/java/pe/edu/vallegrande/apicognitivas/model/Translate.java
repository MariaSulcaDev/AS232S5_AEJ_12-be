package pe.edu.vallegrande.apicognitivas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.edu.vallegrande.apicognitivas.model.dto.TranslateRequestDto;
import pe.edu.vallegrande.apicognitivas.model.dto.TranslateResponseDto;

import java.time.LocalDateTime;

@Document(collection = "deep_translate")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Translate {

    @Id
    private String id;
    private LocalDateTime createdAt;
    private String externalEndpoint; // API externa (RapidAPI)
    private String localEndpoint; // Endpoint local (localhost)
    private TranslateRequestDto request;
    private TranslateResponseDto response;

    // Constructor para facilitar la creación
    public Translate(String externalEndpoint, String localEndpoint, TranslateRequestDto request,
            TranslateResponseDto response) {
        this.externalEndpoint = externalEndpoint;
        this.localEndpoint = localEndpoint;
        this.request = request;
        this.response = response;
        this.createdAt = LocalDateTime.now();
    }
}
