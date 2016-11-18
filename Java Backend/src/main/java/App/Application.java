package App;


import App.model.User;
import App.db.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
public class Application implements CommandLineRunner{
    @Autowired UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    //This code is just to test atm
    @Override
    public void run(String... args) throws Exception {
        User user = (repository.findByUsername("testuser"));
        List<User> users = repository.findAll();
        System.out.println(user.getPassword());


    }





}
