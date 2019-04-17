package web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.Repository.AuctionRepository;
import web.Entity.Auction;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    AuctionRepository repo;

    @GetMapping
    Iterable<Auction> getAuctions(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    Optional<Auction> getAuction(@PathVariable long id){
        return repo.findById(id);
    }

    @GetMapping("/search/{title}")
    List<Auction> searchAuction(@PathVariable String title){
        return repo.findByTitleContaining(title);
    }



    @PostMapping
    void addAuction(@RequestBody Auction body) {
        repo.save(body);
    }
}
