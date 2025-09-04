package pe.edu.vallegrande.apicognitivas.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.apicognitivas.model.Translate;

@Repository
public interface TranslateRepository extends ReactiveMongoRepository<Translate, String> {
}
