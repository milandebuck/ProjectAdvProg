package App;

import db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sun.tools.jar.CommandLine;

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
        model.User user1 = new model.User("supertest", passwordencoder.encode("Azerty123") );
        userRepo.save(user1);
        //System.out.println((user.getPassword()));



    }
}
