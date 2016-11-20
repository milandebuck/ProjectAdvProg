package App.db;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

/**
 *  Configuration of our mongodb
 */


@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "ProjectDB";
    }

    /**
     *TODO Use credentials
     *MongoCredential credential = MongoCredential.createCredential(mongoUser, databaseName, mongoPass.toCharArray());
     *MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential));
     **/
    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1", 27017);
    }

    @Override
    protected String getMappingBasePackage() {
        return "model";
    }


}
