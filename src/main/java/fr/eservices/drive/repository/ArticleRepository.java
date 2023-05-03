package fr.eservices.drive.repository;

import org.springframework.data.repository.CrudRepository;
import fr.eservices.drive.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
    
    Article findById(int id);

}
