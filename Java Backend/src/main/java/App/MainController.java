package App;

import App.authentication.JwtAuthenticationRequest;
import App.authentication.JwtAuthenticationResponse;
import App.authentication.JwtTokenUtil;
import App.authentication.UserService;
import App.logic.CheckResponse;
import App.logic.GetWordsResponse;
import App.registration.UserValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import db.EntryRepository;
import model.Entry;
import model.User;
import model.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@EnableMongoRepositories(basePackages="db")
public class MainController {
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private UserValidator userValidator;

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
     * @return {List<Entry>}
     */
    @RequestMapping("/Entries")
    public Wrapper<List<Entry>> getEntries() {
        List<Entry>  entries = new ArrayList<Entry>();  
        for (Entry entry : repository.findAll()) {
            entries.add(entry);
            System.out.println("entry found");
        }
        return new Wrapper<List<model.Entry>>(true,"get data succeeded",entries);
    }

    
    /**
     * Returns List of set size with random words.
     * @param {string} amount
     * @param {string} from - language
     * @param {string} to - language
     * @return {Wrapper}
     */
    @GetMapping("/Exercise")
    public Wrapper exerciseForm(
            @RequestParam(value="amount", defaultValue="10") String amount,
            @RequestParam(value="from", defaultValue="") String from,
            @RequestParam(value="to", defaultValue="") String to) throws JsonProcessingException {
        return new GetWordsResponse(repository, new String[] { from, to }, amount).listOut();
    }
    
    /**
     * Returns true if given {Entry} is correct.
     * @param {Object} entry
     * @return {Wrapper}
     */
    @PostMapping("/Exercise")
    public Wrapper exerciseSubmit(@RequestBody String input) throws ParseException {
        return new CheckResponse(repository, input).getResult();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        //UsernamePasswordAuthenticationToken sf = new UsernamePasswordAuthenticationToken("name", "password");
        return "Login page";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createUser(@Valid User user, BindingResult br, HttpServletResponse response) {
        userValidator.validate(user, br);
        if (br.hasErrors()) {
            return br.toString();
        }

        System.out.println(user.getUsername());
        userDetailsService.save(user);

        return "success";
    }


}
