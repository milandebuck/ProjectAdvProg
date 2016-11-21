package App.db;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import java.util.ArrayList;
import java.util.List;

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
        MongoCredential credential = MongoCredential.createCredential("user1", "TeamMartini", "Azerty123".toCharArray());
        List<MongoCredential> listCred = new ArrayList<MongoCredential>();
        listCred.add(credential);
        //return new MongoClient(new ServerAddress("127.0.0.1", 27017));
        //travis return
        return new MongoClient(new ServerAddress("127.0.0.1", 27017), listCred);

    }

    @Override
    protected String getMappingBasePackage() {
        return "model";
    }

}
