package web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.Entity.Bid;

import java.util.List;

@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {
    List<Bid> findByAuctionId(int auctionId);
}
