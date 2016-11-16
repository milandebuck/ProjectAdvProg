package App;

import db.MongoConnection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/gettest")
    public GetWordsResponse getWordsResponse(@RequestParam(value="lang", defaultValue="error") String lang, @RequestParam(value="amt", defaultValue="error") String amt) {
        return new GetWordsResponse(lang, Integer.parseInt(amt));
    }

}
