package App;

import db.MongoConnection;
import model.Entry;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
    public List<Entry> exerciseForm(@RequestParam(value="amount") String amount) {
        return new GetWordsResponse("xxx", Integer.parseInt(amount)).getWords();
    }
    
    /**
     *
     * @param entry
     * @return
     */
    @PostMapping("/exercise")
    public Boolean exerciseSubmit(@RequestBody Entry entry) {
        return "eend".equals(entry.getTranslation());
    }
}
