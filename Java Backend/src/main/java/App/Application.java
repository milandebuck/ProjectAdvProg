package App;

import config.MongoConfig;
import db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //This code is just to test atm
    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder passwordencoder = new BCryptPasswordEncoder();
        model.User user1 = new model.User("supertest", passwordencoder.encode("Azerty123"), false );
        userRepo.save(user1);



    }
}
