package web.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String picture;

    private int auctionId;

    public Picture() {

    }

    public String getPicture() {
        return picture;
    }

    public int getAuctionId() {
        return auctionId;
    }

}
