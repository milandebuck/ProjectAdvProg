package App;


import main.java.db.EntryRepository;
import model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@EnableMongoRepositories(basePackages="main.java.db")
public class MainController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private EntryRepository repository;

    @RequestMapping("/")
    public String returnMsg() {

        return "Dit is de API van TeamMartini voor ScrumProject AdvProg";
    }

    @RequestMapping("/Entries")
    public List<Entry> getEntries() {
        List<Entry>  entries = new ArrayList<Entry>();
        for (Entry entry : repository.findAll()) {
            entries.add(entry);
            System.out.println("entry found");
        }
        return entries;
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
