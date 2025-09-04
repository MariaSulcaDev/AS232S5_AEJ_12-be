package pe.edu.vallegrande.apicognitivas.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleVideoDownloaderResponseDto {

     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public static class SimpleChannel {
          private String name;
          private String handle;
          private String avatarUrl;
          private Boolean isVerified;
     }

     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public static class SimpleThumbnail {
          private String url;
          private Integer width;
          private Integer height;
     }

     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public static class SimpleVideo {
          private String url;
          private String quality;
          private String sizeText;
          private String mimeType;
     }

     private String id;
     private String title;
     private String description;
     private SimpleChannel channel;
     private SimpleThumbnail thumbnail; // La de mayor calidad
     private String lengthText;
     private String viewCountText;
     private String publishedTimeText;
     private SimpleVideo video; // El video de mayor calidad
     private Boolean hasSubtitles;

     // Constructor para convertir desde VideoDownloaderResponseDto
     public SimpleVideoDownloaderResponseDto(VideoDownloaderResponseDto response) {
          this.id = response.getId();
          this.title = response.getTitle();
          this.description = response.getDescription();
          this.publishedTimeText = response.getPublishedTimeText();

          // Calcular lengthText desde lengthSeconds
          if (response.getLengthSeconds() != null) {
               int minutes = response.getLengthSeconds() / 60;
               int seconds = response.getLengthSeconds() % 60;
               this.lengthText = String.format("%d:%02d", minutes, seconds);
          }

          // Formatear viewCount
          if (response.getViewCount() != null) {
               this.viewCountText = formatViewCount(response.getViewCount());
          }

          // Canal simplificado
          if (response.getChannel() != null) {
               String avatarUrl = null;
               if (response.getChannel().getAvatar() != null && !response.getChannel().getAvatar().isEmpty()) {
                    // Tomar el primer avatar (o el de mayor resolución)
                    avatarUrl = response.getChannel().getAvatar().stream()
                              .filter(avatar -> avatar.getUrl() != null)
                              .findFirst()
                              .map(VideoDownloaderResponseDto.Thumbnail::getUrl)
                              .orElse(null);
               }

               this.channel = new SimpleChannel(
                         response.getChannel().getName(),
                         response.getChannel().getHandle(),
                         avatarUrl,
                         response.getChannel().getIsVerified());
          }

          // Thumbnail de mayor calidad
          if (response.getThumbnails() != null && !response.getThumbnails().isEmpty()) {
               VideoDownloaderResponseDto.Thumbnail bestThumbnail = response.getThumbnails().stream()
                         .filter(t -> t.getWidth() != null && t.getHeight() != null)
                         .max((t1, t2) -> Integer.compare(t1.getWidth() * t1.getHeight(),
                                   t2.getWidth() * t2.getHeight()))
                         .orElse(response.getThumbnails().get(0));

               this.thumbnail = new SimpleThumbnail(
                         bestThumbnail.getUrl(),
                         bestThumbnail.getWidth(),
                         bestThumbnail.getHeight());
          }

          // Video de mayor calidad
          if (response.getVideos() != null && response.getVideos().getItems() != null
                    && !response.getVideos().getItems().isEmpty()) {
               VideoDownloaderResponseDto.VideoItem bestVideo = response.getVideos().getItems().stream()
                         .filter(v -> v.getUrl() != null && v.getWidth() != null && v.getHeight() != null)
                         .max((v1, v2) -> Integer.compare(v1.getWidth() * v1.getHeight(),
                                   v2.getWidth() * v2.getHeight()))
                         .orElse(response.getVideos().getItems().get(0));

               this.video = new SimpleVideo(
                         bestVideo.getUrl(),
                         bestVideo.getQuality(),
                         bestVideo.getSizeText(),
                         bestVideo.getMimeType());
          }

          // Verificar si tiene subtítulos
          this.hasSubtitles = response.getSubtitles() != null
                    && response.getSubtitles().getItems() != null
                    && !response.getSubtitles().getItems().isEmpty();
     }

     private String formatViewCount(Long viewCount) {
          if (viewCount == null)
               return "0 views";

          if (viewCount >= 1_000_000) {
               return String.format("%.1fM views", viewCount / 1_000_000.0);
          } else if (viewCount >= 1_000) {
               return String.format("%.1fK views", viewCount / 1_000.0);
          } else {
               return viewCount + " views";
          }
     }
}
