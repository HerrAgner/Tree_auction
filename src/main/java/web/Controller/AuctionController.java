package web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import web.Repository.AuctionRepository;
import web.Entity.Auction;
import org.springframework.data.domain.Pageable;


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

    @GetMapping("/length")
    long checkLength(){
        return repo.count();
    }

    @GetMapping("/auctionPage/{pageNumber}")
    Page<Auction> auctionPage (@PathVariable int pageNumber){
        Pageable pageRequest = PageRequest.of(pageNumber, 10, Sort.by("AddedTime").descending());
        Page<Auction> page = repo.findAll(pageRequest);
        return page;
    }


    @GetMapping("/auctionPageContent/{pageNumber}")
    List<Auction> auctionPageContent(@PathVariable int pageNumber){
        Pageable pageRequest = PageRequest.of(pageNumber, 10, Sort.by("AddedTime").descending());
        Page<Auction> page = repo.findAll(pageRequest);
        return page.getContent();
    }


}
