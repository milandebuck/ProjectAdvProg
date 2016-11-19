package db;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


 // The Database Configuration

@EnableMongoRepositories(basePackages="mongodb.repository")
@Configuration
public class SpringMongoConfig {

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        UserCredentials userCredentials = new UserCredentials("user1", "Azerty123");
        return new SimpleMongoDbFactory(new Mongo(), "TeamMartini", userCredentials);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        return mongoTemplate;

    }
}
/*@EnableMongoRepositories(basePackages="mongodb.repository")
@Configuration
public class SpringMongoConfig {
    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(), "words");
    }
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }
}*/
