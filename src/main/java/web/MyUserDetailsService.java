package web;

import org.springframework.context.annotation.Configuration;
import web.Entity.User;
import web.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
public class MyUserDetailsService implements UserDetailsService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public BCryptPasswordEncoder getEncoder() { return encoder; }

    @Autowired
    private UserRepository repository;


    @PostConstruct
    private void createDefaultUsers(){
        if (repository.findDistinctFirstByEmail("rami") == null) {
            addUser("rami", "password", "rami.almhana@yahoo.com", "albadri", "0700566745");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findDistinctFirstByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found by name: " + username);
        }
        return toUserDetails(user);
    }

    public void addUser(String firstname, String password, String email, String lastname, String phone){
        User u = new User();
        u.setFirstName(firstname);
        u.setPassword(encoder.encode(password));
        u.setEmail(email);
        u.setLastName(lastname);
        u.setPhoneNumber(phone);
        try {
            repository.save(u);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getFirstname())
                .password(user.getPassword())
                .roles("USER").build();
    }
}
