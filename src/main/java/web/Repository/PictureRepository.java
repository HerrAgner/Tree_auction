package web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.Entity.Picture;
import web.Entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PictureRepository extends CrudRepository<Picture, Long> {
    List<Picture> findAllByAuctionId(int auctionId);
}
