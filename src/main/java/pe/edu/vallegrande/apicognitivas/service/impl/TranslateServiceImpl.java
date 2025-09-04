package pe.edu.vallegrande.apicognitivas.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.apicognitivas.model.Translate;
import pe.edu.vallegrande.apicognitivas.model.dto.TranslateRequestDto;
import pe.edu.vallegrande.apicognitivas.model.dto.TranslateResponseDto;
import pe.edu.vallegrande.apicognitivas.repository.TranslateRepository;
import pe.edu.vallegrande.apicognitivas.service.TranslateService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslateServiceImpl implements TranslateService {

    private final TranslateRepository translateRepository;

    @Qualifier("translateWebClient")
    private final WebClient translateWebClient;

    @Value("${translate.api.url}")
    private String translateUrl;

    @Value("${translate.api.host}")
    private String rapidApiHost;

    @Value("${translate.api.key}")
    private String rapidApiKey;

    @Override
    public Mono<TranslateResponseDto> translateText(TranslateRequestDto request) {
        log.info("Iniciando traducción: {} -> {} para texto: {}",
                request.getSource(), request.getTarget(), request.getQ());

        String localEndpoint = "http://localhost:8081/api/cognitive/translate";

        return translateWebClient.post()
                .uri("")
                .header("x-rapidapi-host", rapidApiHost)
                .header("x-rapidapi-key", rapidApiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TranslateResponseDto.class)
                .flatMap(response -> {
                    Translate translate = new Translate(translateUrl, localEndpoint, request, response);
                    return translateRepository.save(translate)
                            .doOnNext(saved -> log.info("Traducción guardada exitosamente con ID: {}", saved.getId()))
                            .then(Mono.just(response));
                })
                .doOnError(error -> {
                    log.error("Error al realizar la traducción: {}", error.getMessage(), error);
                });
    }

    @Override
    public Flux<Translate> getAllTranslations() {
        return translateRepository.findAll();
    }

    @Override
    public Mono<Translate> getTranslationById(String id) {
        return translateRepository.findById(id);
    }
}
