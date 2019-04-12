package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostRepository repo;

    @GetMapping
    Iterable<Post> getPosts(){
        return repo.findAll();
    }

    @PostMapping
    void addPost(@RequestBody Post body) {
        repo.save(body);
    }
}
