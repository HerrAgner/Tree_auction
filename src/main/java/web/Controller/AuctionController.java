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

import java.sql.Timestamp;
import java.time.Instant;
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

    @GetMapping("/auctionPage/{pageNumber}")
    Page<Auction> auctionPage (@PathVariable int pageNumber){
        Timestamp now = Timestamp.from(Instant.now());
        Pageable pageRequest = PageRequest.of(pageNumber, 10, Sort.by("AddedTime").descending());
        Page<Auction> page = repo.findByEndTimeAfter(pageRequest, now);
        return page;
    }

    @GetMapping("/auctionPage/{searchTitle}/{pageNumber}")
    Page<Auction> searchPage (@PathVariable int pageNumber, @PathVariable String searchTitle){
        Timestamp now = Timestamp.from(Instant.now());
        Pageable pageRequest = PageRequest.of(pageNumber, 10, Sort.by("AddedTime").descending());
        Page<Auction> searchPage = repo.findByTitleContainingAndEndTimeAfter(searchTitle, now, pageRequest);
        return searchPage;
    }

}
