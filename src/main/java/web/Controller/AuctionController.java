package web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.Repository.AuctionRepository;
import web.Entity.Auction;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    AuctionRepository repo;

    @GetMapping
    Iterable<Auction> getAuctions(){
        return repo.findAll();
    }

    @PostMapping
    void addAuction(@RequestBody Auction body) {
        repo.save(body);
    }
}
