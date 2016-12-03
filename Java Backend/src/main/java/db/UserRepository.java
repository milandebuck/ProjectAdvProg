package db;

import model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Create a repository for the User model
 * You do NOT need to implement the methods
 * Spring data creates this on the fly
 */

public interface UserRepository extends MongoRepository<User, String> {

    @Bean
    public User findByUsername(String username);

}