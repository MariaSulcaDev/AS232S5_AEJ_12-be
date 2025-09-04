package pe.edu.vallegrande.apicognitivas.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.apicognitivas.model.VideoDownloader;
import pe.edu.vallegrande.apicognitivas.model.dto.VideoDownloaderRequestDto;
import pe.edu.vallegrande.apicognitivas.model.dto.VideoDownloaderResponseDto;
import pe.edu.vallegrande.apicognitivas.repository.VideoDownloaderRepository;
import pe.edu.vallegrande.apicognitivas.service.VideoDownloaderService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VideoDownloaderServiceImpl implements VideoDownloaderService {

     private final WebClient youtubeWebClient;
     private final VideoDownloaderRepository repository;

     public VideoDownloaderServiceImpl(@Qualifier("youtubeWebClient") WebClient youtubeWebClient,
               VideoDownloaderRepository repository) {
          this.youtubeWebClient = youtubeWebClient;
          this.repository = repository;
     }

     @Value("${youtube.api.url}")
     private String youtubeApiUrl;

     @Value("${youtube.api.key}")
     private String rapidApiKey;

     @Value("${youtube.api.host}")
     private String rapidApiHost;

     @Override
     public Mono<VideoDownloaderResponseDto> getVideoDetails(VideoDownloaderRequestDto request) {
          log.info("Obteniendo detalles del video: {}", request.getVideoId());

          String externalEndpoint = String.format("%s?videoId=%s&videos=%s",
                    youtubeApiUrl, request.getVideoId(), request.getVideos());
          String localEndpoint = "/api/cognitive/video-details";

          return youtubeWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                              .path("")
                              .queryParam("videoId", request.getVideoId())
                              .queryParam("urlAccess",
                                        request.getUrlAccess() != null ? request.getUrlAccess() : "normal")
                              .queryParam("videos", request.getVideos() != null ? request.getVideos() : "auto")
                              .queryParam("audios", request.getAudios() != null ? request.getAudios() : "auto")
                              .build())
                    .header("X-RapidAPI-Key", rapidApiKey)
                    .header("X-RapidAPI-Host", rapidApiHost)
                    .retrieve()
                    .bodyToMono(VideoDownloaderResponseDto.class)
                    .map(this::processResponse)
                    .flatMap(response -> {
                         // Guardar en la base de datos
                         VideoDownloader videoDownloader = new VideoDownloader(
                                   externalEndpoint, localEndpoint, request, response);

                         return repository.save(videoDownloader)
                                   .doOnSuccess(
                                             saved -> log.info("Video downloader guardado con ID: {}", saved.getId()))
                                   .map(saved -> response);
                    })
                    .doOnError(
                              error -> log.error("Error al obtener detalles del video: {}", error.getMessage(), error));
     }

     @Override
     public Flux<VideoDownloader> getAllVideoDownloads() {
          log.info("Obteniendo todos los video downloads");
          return repository.findAll();
     }

     @Override
     public Mono<VideoDownloader> getVideoDownloadById(String id) {
          log.info("Obteniendo video download por ID: {}", id);
          return repository.findById(id);
     }

     private VideoDownloaderResponseDto processResponse(VideoDownloaderResponseDto response) {
          // Procesar channel para obtener la imagen con mayor dimensión
          if (response.getChannel() != null && response.getChannel().getAvatar() != null) {
               // Ya viene procesado desde la API, pero podríamos validar aquí si es necesario
          }

          // Procesar thumbnails para obtener la de mayor calidad y dimensión
          if (response.getThumbnails() != null && !response.getThumbnails().isEmpty()) {
               List<VideoDownloaderResponseDto.Thumbnail> sortedThumbnails = response.getThumbnails().stream()
                         .filter(t -> t.getWidth() != null && t.getHeight() != null)
                         .sorted(Comparator
                                   .comparing((VideoDownloaderResponseDto.Thumbnail t) -> t.getWidth() * t.getHeight())
                                   .reversed())
                         .collect(Collectors.toList());

               if (!sortedThumbnails.isEmpty()) {
                    response.setThumbnails(sortedThumbnails);
               }
          }

          // Procesar videos para obtener solo la URL del video con mayor calidad
          if (response.getVideos() != null && response.getVideos().getItems() != null
                    && !response.getVideos().getItems().isEmpty()) {
               List<VideoDownloaderResponseDto.VideoItem> sortedVideos = response.getVideos().getItems().stream()
                         .filter(v -> v.getUrl() != null && v.getWidth() != null && v.getHeight() != null)
                         .sorted(Comparator
                                   .comparing((VideoDownloaderResponseDto.VideoItem v) -> v.getWidth() * v.getHeight())
                                   .reversed())
                         .collect(Collectors.toList());

               if (!sortedVideos.isEmpty()) {
                    response.getVideos().setItems(sortedVideos);
               }
          }

          // No guardamos audios según la solicitud
          // response.setAudios(null); // Si tuviéramos este campo lo eliminaríamos

          return response;
     }
}
