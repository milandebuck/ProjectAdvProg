package config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by milan on 21.11.16.
 */
@Configuration
@EnableMongoRepositories(basePackages = "main.java.db")
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "TeamMartini";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        /*List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(
                MongoCredential.createScramSha1Credential(
                        "TeamMartini",
                        "TeamMartini",
                        "Azerty123".toCharArray()
                ));*/
        return new MongoClient(new ServerAddress("localhost", 27017));
    }

    @Override
    protected String getMappingBasePackage() {
        return "main.java.db";
    }
}
