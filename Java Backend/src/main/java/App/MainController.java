package App;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MainController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

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
