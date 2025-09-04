package pe.edu.vallegrande.apicognitivas.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDownloaderRequestDto {
     private String videoId;
     private String urlAccess;
     private String videos;
     private String audios;
}
