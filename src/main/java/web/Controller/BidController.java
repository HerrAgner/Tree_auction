package web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.Repository.BidRepository;
import web.Entity.Bid;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    BidRepository repo;

    @GetMapping
    Iterable<Bid> getBids(){
        return repo.findAll();
    }

    @GetMapping("/{auctionId}")
    Iterable<Bid> getBids(@PathVariable int auctionId){
        return repo.findByAuctionId(auctionId);
    }

    @PostMapping
    void addBid(@RequestBody Bid body) {
        repo.save(body);
    }
}
