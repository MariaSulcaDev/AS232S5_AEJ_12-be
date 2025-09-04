package pe.edu.vallegrande.apicognitivas.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.apicognitivas.model.Translate;
import pe.edu.vallegrande.apicognitivas.model.VideoDownloader;
import pe.edu.vallegrande.apicognitivas.model.dto.SimpleTranslateResponseDto;
import pe.edu.vallegrande.apicognitivas.model.dto.SimpleVideoDownloaderResponseDto;
import pe.edu.vallegrande.apicognitivas.model.dto.TranslateRequestDto;
import pe.edu.vallegrande.apicognitivas.model.dto.VideoDownloaderRequestDto;
import pe.edu.vallegrande.apicognitivas.service.TranslateService;
import pe.edu.vallegrande.apicognitivas.service.VideoDownloaderService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cognitive")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CognitiveRest {

     private final TranslateService translateService;
     private final VideoDownloaderService videoDownloaderService;

     @PostMapping("/translate")
     public Mono<ResponseEntity<SimpleTranslateResponseDto>> translateText(@RequestBody TranslateRequestDto request) {
          log.info("Recibida solicitud de traducción: {} -> {} para: '{}'",
                    request.getSource(), request.getTarget(), request.getQ());

          return translateService.translateText(request)
                    .map(response -> {
                         log.info("Traducción completada exitosamente");
                         // Convertir a respuesta simplificada
                         SimpleTranslateResponseDto simpleResponse = new SimpleTranslateResponseDto(response);
                         return ResponseEntity.ok(simpleResponse);
                    })
                    .onErrorResume(error -> {
                         log.error("Error en la traducción: {}", error.getMessage(), error);
                         return Mono.just(ResponseEntity.badRequest().build());
                    });
     }

     @GetMapping("/translations")
     public Flux<Translate> getAllTranslations() {
          log.info("Obteniendo todas las traducciones");
          return translateService.getAllTranslations();
     }

     @GetMapping("/translations/{id}")
     public Mono<ResponseEntity<Translate>> getTranslationById(@PathVariable String id) {
          log.info("Obteniendo traducción por ID: {}", id);

          return translateService.getTranslationById(id)
                    .map(translate -> ResponseEntity.ok(translate))
                    .defaultIfEmpty(ResponseEntity.notFound().build());
     }

     // === VIDEO DOWNLOADER ENDPOINTS ===

     @GetMapping("/video-details")
     public Mono<ResponseEntity<SimpleVideoDownloaderResponseDto>> getVideoDetails(
               @RequestParam String videoId,
               @RequestParam(defaultValue = "normal") String urlAccess,
               @RequestParam(defaultValue = "auto") String videos,
               @RequestParam(defaultValue = "auto") String audios) {

          log.info("Recibida solicitud de detalles de video: {}", videoId);

          VideoDownloaderRequestDto request = new VideoDownloaderRequestDto(videoId, urlAccess, videos, audios);

          return videoDownloaderService.getVideoDetails(request)
                    .map(response -> {
                         log.info("Detalles de video obtenidos exitosamente");
                         // Convertir a respuesta simplificada
                         SimpleVideoDownloaderResponseDto simpleResponse = new SimpleVideoDownloaderResponseDto(
                                   response);
                         return ResponseEntity.ok(simpleResponse);
                    })
                    .onErrorResume(error -> {
                         log.error("Error al obtener detalles del video: {}", error.getMessage(), error);
                         return Mono.just(ResponseEntity.badRequest().build());
                    });
     }

     @GetMapping("/video-downloads")
     public Flux<VideoDownloader> getAllVideoDownloads() {
          log.info("Obteniendo todos los video downloads");
          return videoDownloaderService.getAllVideoDownloads();
     }

     @GetMapping("/video-downloads/{id}")
     public Mono<ResponseEntity<VideoDownloader>> getVideoDownloadById(@PathVariable String id) {
          log.info("Obteniendo video download por ID: {}", id);

          return videoDownloaderService.getVideoDownloadById(id)
                    .map(videoDownload -> ResponseEntity.ok(videoDownload))
                    .defaultIfEmpty(ResponseEntity.notFound().build());
     }
}
