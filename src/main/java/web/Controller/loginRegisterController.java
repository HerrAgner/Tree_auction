package web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.Entity.User;
import web.MyUserDetailsService;
import web.Repository.UserRepository;

@RestController
@RequestMapping("/login")
public class loginRegisterController {

    @Autowired
    UserRepository repo;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @GetMapping("/users")
    Iterable<User> getUsers(){
        return repo.findAll();
    }

    @GetMapping("/{email}")
    User getUserEmail(@PathVariable String email){
        return repo.findOneByEmail(email);
    }

    @PostMapping
    void addUser(@RequestBody User body) {
        body.setPassword(myUserDetailsService.getEncoder().encode(body.getPassword()));
        repo.save(body);
    }
}
