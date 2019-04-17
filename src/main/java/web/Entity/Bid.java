package web.Entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Bid {
    @Id
    private Integer auctionId = null;
    private String bidder_id = null;
    private Float amount = null;
    private Timestamp time = null;
    

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

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public void setBidder_id(String bidder_id) {
        this.bidder_id = bidder_id;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
