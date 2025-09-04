package pe.edu.vallegrande.apicognitivas.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.apicognitivas.model.VideoDownloader;

@Repository
public interface VideoDownloaderRepository extends ReactiveMongoRepository<VideoDownloader, String> {
}
