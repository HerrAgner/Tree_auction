package web.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.Entity.Auction;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Auction findById(int id);
//    List<Auction> findAllByTitle(String title);
    List<Auction> findByTitleContaining(String title);

    Page<Auction> findByTitleContainingAndEndTimeAfter(String title, Timestamp now, Pageable pageable);

    Page<Auction> findByEndTimeAfter(Pageable pageable, Timestamp now);



}
