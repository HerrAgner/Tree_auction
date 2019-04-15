package web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.Entity.Auction;

@Repository
public interface AuctionRepository extends CrudRepository<Auction, Long> {

}
