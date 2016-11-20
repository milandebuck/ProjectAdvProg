package App.Authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.mongodb.client.model.Filters.and;

/**
 * Created by micha on 11/19/2016.
 */

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //Not sure whether to put this here TODO
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Used to allow users to use the resourcefolder which should contain CSS, images...
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**"); //
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * At the moment you need to login for every page except the homepage and of course the login page
         * Every other page will reroute to the loginpage
         **/
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                //.defaultSuccessUrl("")
                //.failureUrl("")
                .permitAll()
            .and()
                .logout()
                .permitAll()
            .and()
                .csrf().disable();
    }



}
