package web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.Entity.Picture;
import web.Repository.PictureRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/pictures")
public class PictureController {

    @Autowired
    PictureRepository repo;

    @GetMapping
    Iterable<Picture> getPicture(){
        return repo.findAll();
    }

    @GetMapping("/{auctionId}")
    List<Picture> getPicture(@PathVariable int auctionId){
        return repo.findAllByAuctionId(auctionId);
    }

    @PostMapping
    void addPicture(@RequestBody Picture body) {
        repo.save(body);
    }
}