package web.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Bid implements MessageInterface{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer auctionId = null;
    private String bidderId = null;
    private Float amount = null;
    private Timestamp time = null;

    public Bid() {

    }

    public int getAuction_id() {
        return auctionId;
    }

    public String getBidderId() {
        return bidderId;
    }

    public float getAmount() {
        return amount;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
