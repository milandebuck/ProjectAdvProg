package App.repository;

import model.WordList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordListRepository extends MongoRepository<WordList, String> {


}