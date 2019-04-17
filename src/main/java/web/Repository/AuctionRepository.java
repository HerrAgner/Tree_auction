package web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.Entity.Auction;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Auction findById(int id);
//    List<Auction> findAllByTitle(String title);
    List<Auction> findByTitleContaining(String title);
}
