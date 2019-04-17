package web.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "bids")
public class Bid {
    @Id
    private long id;
    private Integer auctionId = null;
    private final String bidder_id = null;
    private final Float amount = null;
    private final Timestamp time = null;

    public Bid() {

    }

    public int getAuction_id() {
        return auctionId;
    }

    public String getBidder_id() {
        return bidder_id;
    }

    public float getAmount() {
        return amount;
    }

    public Timestamp getTime() {
        return time;
    }
}
