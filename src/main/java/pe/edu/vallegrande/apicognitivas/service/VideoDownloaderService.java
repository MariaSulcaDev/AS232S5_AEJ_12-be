package pe.edu.vallegrande.apicognitivas.service;

import pe.edu.vallegrande.apicognitivas.model.VideoDownloader;
import pe.edu.vallegrande.apicognitivas.model.dto.VideoDownloaderRequestDto;
import pe.edu.vallegrande.apicognitivas.model.dto.VideoDownloaderResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VideoDownloaderService {

     Mono<VideoDownloaderResponseDto> getVideoDetails(VideoDownloaderRequestDto request);

     Flux<VideoDownloader> getAllVideoDownloads();

     Mono<VideoDownloader> getVideoDownloadById(String id);
}
