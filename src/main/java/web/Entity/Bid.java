package web.Entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bids")
public class Bid {
    @Id
    private Integer auctionId = null;
    private final String bidder_id = null;
    private final Float amount = null;
    private final Timestamp time = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "auction_id",
            insertable = false,
            updatable = false

    )
    @Fetch(FetchMode.JOIN)
    private FullAuction fullAuction;

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
