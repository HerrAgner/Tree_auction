package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
