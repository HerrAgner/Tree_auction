package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
