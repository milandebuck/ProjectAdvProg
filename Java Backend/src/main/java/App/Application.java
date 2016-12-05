package App;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Starts Spring application.
     * @param args
     * @throws Exception
     */
    //This code is just to test atm
    @Override
    public void run(String... args) throws Exception {

    }
}
