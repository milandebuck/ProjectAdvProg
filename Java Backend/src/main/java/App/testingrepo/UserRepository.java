package App.testingrepo;

import model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Create a repository for the User model
 * You do NOT need to implement the methods
 * Spring data creates this on the fly
 */

@org.springframework.stereotype.Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

}