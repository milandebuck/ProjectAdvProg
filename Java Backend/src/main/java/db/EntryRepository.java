package main.java.db;

/**
 * Created by milan on 20.11.16.
 */


import java.util.List;
import model.Entry;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntryRepository extends MongoRepository<Entry, String> {

    @Bean
    public Entry findByWord(String Word);
    @Bean
    public List<Entry> findByTranslation(String translation);

}
