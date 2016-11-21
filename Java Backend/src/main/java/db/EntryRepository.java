package db;

/**
 * Created by milan on 20.11.16.
 */


import model.Entry;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EntryRepository extends MongoRepository<Entry, String> {

    @Bean
    public Entry findByWord(String Word);
    @Bean
    public List<Entry> findByTranslation(String translation);

}
