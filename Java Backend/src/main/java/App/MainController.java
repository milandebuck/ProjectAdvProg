package App;

import db.MongoConnection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;
import model.Exercise;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class MainController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    MongoConnection mongoConnection = new MongoConnection();

    @RequestMapping("/")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
    
    /**
     *
     * @return 
     */
    @GetMapping("/exercise")
    public Exercise exerciseForm() {
        return new Exercise("duck", "English", "Dutch");
    }
    
    /**
     *
     * @param exercise
     * @return
     */
    @PostMapping("/exercise")
    public Boolean exerciseSubmit(@RequestBody Exercise exercise) {
        return "eend".equals(exercise.getAnswer());
    }
}
