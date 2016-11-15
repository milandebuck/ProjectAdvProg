package main.java.db;
/**
import main.java.model.Word;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;



 * Uses the MongoTemplate to create queries.
 * Use this service to get words of the database.
public class WordService {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");

        public List<Word> findAll() {
            return mongoOperation.findAll(Word.class);
        }

        public Word findOne(String word) {
            Query query = new Query(Criteria.where("word").is(word));
            return mongoOperation.findOne(query, Word.class);
        }

        public void insertOne(Word word) {
            mongoOperation.save(word);
        }
}*/
