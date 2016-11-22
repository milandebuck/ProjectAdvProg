package App.Authentication;

/**
 * Created by micha on 11/22/2016.
 */

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}

