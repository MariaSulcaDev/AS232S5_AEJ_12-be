package pe.edu.vallegrande.apicognitivas.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.apicognitivas.model.Translate;
import pe.edu.vallegrande.apicognitivas.model.dto.TranslateRequestDto;
import pe.edu.vallegrande.apicognitivas.model.dto.TranslateResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface TranslateService {

     Mono<TranslateResponseDto> translateText(TranslateRequestDto request);

     Flux<Translate> getAllTranslations();

     Mono<Translate> getTranslationById(String id);
}
