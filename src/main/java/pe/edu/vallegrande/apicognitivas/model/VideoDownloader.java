package pe.edu.vallegrande.apicognitivas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.edu.vallegrande.apicognitivas.model.dto.VideoDownloaderRequestDto;
import pe.edu.vallegrande.apicognitivas.model.dto.VideoDownloaderResponseDto;

import java.time.LocalDateTime;

@Document(collection = "video_downloader")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VideoDownloader {

     @Id
     private String id;
     private LocalDateTime createdAt;
     private String externalEndpoint;
     private String localEndpoint;
     private VideoDownloaderRequestDto request;
     private VideoDownloaderResponseDto response;

     public VideoDownloader(String externalEndpoint, String localEndpoint, VideoDownloaderRequestDto request,
               VideoDownloaderResponseDto response) {
          this.externalEndpoint = externalEndpoint;
          this.localEndpoint = localEndpoint;
          this.request = request;
          this.response = response;
          this.createdAt = LocalDateTime.now();
     }
}
