package pe.edu.vallegrande.apicognitivas.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDownloaderResponseDto {

     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public static class Thumbnail {
          private String url;
          private Integer width;
          private Integer height;
     }

     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public static class Channel {
          private String type;
          private String id;
          private String name;
          private String handle;
          private String url;
          private Boolean isVerified;
          private Boolean isVerifiedArtist;
          private String subscriberCountText;
          private List<Thumbnail> avatar;
     }

     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public static class VideoItem {
          private String url;
          private Long lengthMs;
          private String mimeType;
          private String extension;
          private Long lastModified;
          private Long size;
          private String sizeText;
          private Boolean hasAudio;
          private String quality;
          private Integer width;
          private Integer height;
     }

     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public static class Videos {
          private String errorId;
          private Long expiration;
          private List<VideoItem> items;
     }

     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public static class Subtitle {
          private String url;
          private String code;
          private String text;
     }

     @Data
     @NoArgsConstructor
     @AllArgsConstructor
     public static class Subtitles {
          private String errorId;
          private Long expiration;
          private List<Subtitle> items;
     }

     // Campos principales del video
     private String errorId;
     private String type;
     private String id;
     private String title;
     private String description;
     private Channel channel;
     private List<Thumbnail> thumbnails;
     private Integer lengthSeconds;
     private Long viewCount;
     private Long likeCount;
     private String publishedTime;
     private String publishedTimeText;
     private Boolean isLiveStream;
     private Boolean isLiveNow;
     private Boolean isRegionRestricted;
     private Boolean isUnlisted;
     private Boolean isCommentDisabled;
     private String commentCountText;
     private Videos videos;
     private Subtitles subtitles;
}
