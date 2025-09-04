package pe.edu.vallegrande.apicognitivas.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslateRequestDto {
     private String q; // Texto a traducir
     private String source; // Idioma origen
     private String target; // Idioma destino
}
