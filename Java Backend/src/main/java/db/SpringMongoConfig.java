package db;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;


// The Database Configuration

@EnableMongoRepositories(basePackages="mongodb.repository")
@Configuration
public class SpringMongoConfig {

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        MongoCredential userCredentials = MongoCredential.createScramSha1Credential("user1","TeamMartini", "Azerty123".toCharArray());
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(userCredentials));
        return new SimpleMongoDbFactory(client,"TeamMartini");
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
