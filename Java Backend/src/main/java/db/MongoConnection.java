package db;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

public class MongoConnection {
    @Bean
    public static DB Connect(){
        //Connect to mongo database and return entity
        MongoClient mongoClient = null;
        MongoCredential credential = null;
        try {
            credential = MongoCredential.createMongoCRCredential("user1", "TeamMartini", "Azerty123".toCharArray());
            mongoClient = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));
            System.out.println("test");
        }
        catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        DB db = mongoClient.getDB("TeamMartini");
        return db;
    }
}

