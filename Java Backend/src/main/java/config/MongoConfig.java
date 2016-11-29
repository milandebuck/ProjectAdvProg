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
        return "teammartini";
    }

    @Override
    public Mongo mongo() throws Exception {
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(
                MongoCredential.createScramSha1Credential(
                        "TeamMartini",
                        "teammartini",
                        "Azerty123".toCharArray()
                ));
        return new MongoClient(new ServerAddress("ds113958.mlab.com", 13958),credentials);
    }

    @Override
    protected String getMappingBasePackage() {
        return "main.java.db";
    }
}
