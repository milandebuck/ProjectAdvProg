package config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milan on 21.11.16.
 */

@Configuration
public class MongoConfig {

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(
                MongoCredential.createScramSha1Credential(
                        "TeamMartini",
                        "teammartini",
                        "Azerty123".toCharArray()
                )
        );

        return new SimpleMongoDbFactory(new MongoClient(new ServerAddress("ds113958.mlab.com", 13958), credentials), "teammartini");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate((mongoDbFactory()));
        return mongoTemplate;
    }
}
