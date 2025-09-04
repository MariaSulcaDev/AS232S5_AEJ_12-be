package pe.edu.vallegrande.apicognitivas.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslateResponseDto {
     private Data data;

     @lombok.Data
     @NoArgsConstructor
     @AllArgsConstructor
     public static class Data {
          private Translations translations;
     }

     @lombok.Data
     @NoArgsConstructor
     @AllArgsConstructor
     public static class Translations {
          private List<String> translatedText;
     }
}
