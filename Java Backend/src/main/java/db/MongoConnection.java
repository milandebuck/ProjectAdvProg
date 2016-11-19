package db;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;

public class MongoConnection {
    @Bean
    public MongoOperations Connect(){
        //Connect to mongo database and return entity
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
        return mongoOperation;
    }
}

/*public class MongoConnection {
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
<<<<<<< HEAD
}

=======
}*/
>>>>>>> f9893ac076f1e33f4a210e8729a1a70c720cacbc
