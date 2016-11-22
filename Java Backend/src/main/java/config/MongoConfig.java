package config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

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
    public Mongo mongo() throws Exception {
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(
                MongoCredential.createMongoCRCredential(
                        "user1",
                        "TeamMartini",
                        "Azerty123".toCharArray()
                ));
        return new MongoClient(new ServerAddress("localhost", 27017),credentials);
    }

    @Override
    protected String getMappingBasePackage() {
        return "main.java.db";
    }
}
