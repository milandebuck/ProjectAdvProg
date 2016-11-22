package App;

import db.EntryRepository;
import model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.expression.ParseException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@EnableMongoRepositories(basePackages="db")
public class MainController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private EntryRepository repository;

    /**
     * Returns message for diagnostics purposes.
     * @return {string}
     */
    @RequestMapping("/")
    public String returnMsg() {

        return "Dit is de API van TeamMartini voor ScrumProject AdvProg";
    }

    /**
     *  Returns List of all database entries.
     * @return {List<Entry}
     */
    @RequestMapping("/Entries")
    public List<Entry> getEntries() {
        List<Entry>  entries = new ArrayList<>();
        for (Entry entry : repository.findAll()) {
            entries.add(entry);
            System.out.println("entry found");
        }
        return entries;
    }

    
    /**
     * Returns List of set size with random words.
     * @param {string} amount
     * @param {string} from - language
     * @param {string} to - language
     * @return {List<Entry>}
     */
    @GetMapping("/exercise")
    public List<Entry> exerciseForm(
            @RequestParam(value="amount", defaultValue="10") String amount,
            @RequestParam(value="from", defaultValue="") String from,
            @RequestParam(value="to", defaultValue="") String to) {
        return new GetWordsResponse(repository, new String[] { from, to }, amount).getWords();
    }
    
    /**
     * Returns true if given {Entry} is correct.
     * @param {Entry} entry
     * @return {boolean}
     */
    @PostMapping("/exercise")
    public Boolean exerciseSubmit(@RequestBody Entry entry) throws ParseException {
        return repository.findByWord(entry.getWord()).getTranslation().equals(entry.getTranslation());
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        //UsernamePasswordAuthenticationToken sf = new UsernamePasswordAuthenticationToken("name", "password");
        return "Login page";
    }

//Login post is provided by Spring.

    @RequestMapping(value="/amIloggedin", method = RequestMethod.GET)
    public String printUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        if (name != "anonymousUser") return name;
        return "not logged in";

    }
}
