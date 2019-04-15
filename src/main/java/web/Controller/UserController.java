package web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.Entity.User;
import web.Repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository repo;

    @GetMapping
    Iterable<User> getUsers(){
        return repo.findAll();
    }

    @PostMapping
    void addUser(@RequestBody User body) {
        repo.save(body);
    }
}
