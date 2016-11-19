package App;


import main.java.db.DataAcces;
import model.Entry;
import main.java.db.DataAcces;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class MainController {
    private DataAcces db = new DataAcces();
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/")
    public List<Entry> getEntries() {
        return db.getEntries();
    }
    
    /**
     *
     * @return 

    @GetMapping("/exercise")
    public List<Entry> exerciseForm(@RequestParam(value="amount") String amount) {
        return new GetWordsResponse("xxx", Integer.parseInt(amount)).getWords();
    }
    
    /**
     *
     * @param entry
     * @return
    @PostMapping("/exercise")
    public Boolean exerciseSubmit(@RequestBody Entry entry) {
        return "eend".equals(entry.getTranslation());
    }
    */
}
