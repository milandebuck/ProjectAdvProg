package App;

import App.authentication.JwtAuthenticationRequest;
import App.authentication.JwtAuthenticationResponse;
import App.authentication.JwtTokenUtil;
import App.authentication.UserService;
import App.logic.*;
import App.registration.UserValidator;
import App.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.Entry;
import model.User;
import model.Wrapper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Controller for all major routes.
 */
@CrossOrigin
@RestController
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
    private UserRepository userRepo;

    /**
     * Returns message for diagnostics purposes.
     * @return "Dit is de API van TeamMartini voor ScrumProject AdvProg"
     */
    @RequestMapping("/")
    public String returnMsg() {

        return "Dit is de API van TeamMartini voor ScrumProject AdvProg";
    }

    /**
     * Returns List of all database entries.
     * @return list of entries
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/Entries")
    public Wrapper<List<Entry>> getEntries() {
        //DB-connection
        MongoOperations mongoOperations = Tools.getMongoOperations();

        List<Entry>  entries = new ArrayList<Entry>();  
        for (Entry entry : mongoOperations.findAll(Entry.class, "entries")) {
            entries.add(entry);
            System.out.println("entry found");
        }
        return new Wrapper<List<model.Entry>>(true,"get data succeeded",entries);
    }

    
    /**
     * Returns List of set size with random words.
     * @param amount amount of words
     * @param from language to translate from
     * @param to language to translate to
     * @return list of entries
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/Exercise")
    public Wrapper exerciseCreate(
            @RequestParam(value="amount", defaultValue="10") String amount,
            @RequestParam(value="from", defaultValue="") String from,
            @RequestParam(value="to", defaultValue="") String to) throws JsonProcessingException {
        return new GetWordsResponse(new String[] { from, to }, amount).listOut();
    }
    
    /**
     * Returns score when list of entries is given.
     * @param input list of entries
     * @param token authentication
     * @return score, maximum score, list of corrections.
     * @throws ParseException
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/Exercise")
    public Wrapper exerciseCheck(@RequestBody String input, @RequestParam("token") String token) throws ParseException {
        //if (token == null) throw new IllegalArgumentException("token is null in exercisecontroller");

        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();

        return new CheckResponse(user, input).getResult();
    }

    /**
     * Saves user made list.
     * @param input user made list
     * @param token authentication
     * @return failed or success
     * @throws ParseException
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/SaveList")
    public Wrapper saveList(@RequestBody String input, @RequestParam("token") String token) throws ParseException {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new SaveWordList(user, input).getConfirmation();
    }

    /**
     * Returns list of test results of user, sorted on languages.
     * @param token authentication
     * @return list of test results
     * @throws ParseException
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/GetUserResults")
    public Wrapper userResults(@RequestParam("token") String token) throws ParseException {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GetResults(user).userResults();
    }

    /**
     * Shows the login page
     * @return "Login page"
     **/
    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        //UsernamePasswordAuthenticationToken sf = new UsernamePasswordAuthenticationToken("name", "password");

        return "Login page";
    }

    /**
     *   Loaded when user tries to login
     *
     *   if successful: return a jwt
     *   if fail: return badcredentialserror
     *   @param authenticationRequest
     *   @return jwt
     *   @throws AuthenticationException
     **/
    @CrossOrigin
    @RequestMapping(value = "/Login", method = RequestMethod.POST)
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

    /**
     * Register user
     * @return Wrapper
     **/
    @CrossOrigin
    @RequestMapping(value = "/Registration", method = RequestMethod.POST)
    public Wrapper createUser(@RequestBody User user)  {
//        userValidator.validate(user, br);
//        if (br.hasErrors()) {
//
//            List<ValidationError> errors = new ArrayList();
//            List<ObjectError> errorsInForm = br.getAllErrors();
//
//
//            errorsInForm.forEach(error->{
//                String [] errorCodes = error.getCodes();
//                int index = errorCodes[0].lastIndexOf('.');
//                errors.add(new ValidationError(errorCodes[0].substring(index + 1), error.getDefaultMessage()));
//            });
//
//            return new Wrapper(false, "Form contains errors!", errors);
//        }

        User userInDB = userRepo.findByUsername(user.getUsername());
        if (userInDB != null) return new Wrapper(false, "Username already exists", new Object());

        userDetailsService.save(new User(user.getUsername(), user.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new Wrapper(true, "Registration success!", token);
    }

    /**
     * Creates group of users for teacher
     * @param token authentication
     * @param idList id to delete wordlist
     * @return Wrapper
     * @throws ParseException
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/DeleteList")
    public Wrapper deleteList(@RequestParam("idList") String idList, @RequestParam("token") String token) throws ParseException {
       try {
           String username = jwtTokenUtil.getUsernameFromToken(token);
           model.User user = userRepo.findByUsername(username);
           user.removeFromWordLists(new ObjectId(idList));
           userRepo.save(user);
       }
       catch(Exception e) {
           return new Wrapper(true, "Error: " + e.toString(), new Object());
       }
        return new Wrapper(true, "List is successfully deleted!", new Object());
    }
}
