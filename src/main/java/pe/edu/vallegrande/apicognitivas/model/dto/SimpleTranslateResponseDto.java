package pe.edu.vallegrande.apicognitivas.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTranslateResponseDto {
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

     // Constructor que convierte desde TranslateResponseDto
     public SimpleTranslateResponseDto(TranslateResponseDto originalResponse) {
          if (originalResponse != null && originalResponse.getData() != null) {
               this.data = new Data();
               this.data.translations = new Translations();
               this.data.translations.translatedText = originalResponse.getData().getTranslations().getTranslatedText();
          }
     }
}
