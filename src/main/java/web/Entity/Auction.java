package web.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title = null;
    private String description = null;
    private Timestamp end_time = null;
    private Timestamp added_time = null;
    private String seller_id = null;

    private Float start_price = null;

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public void setAdded_time(Timestamp added_time) {
        this.added_time = added_time;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public void setStart_price(Float start_price) {
        this.start_price = start_price;
    }



    public Auction() {

    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public Timestamp getAdded_time() {
        return added_time;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public float getStart_price() {
        return start_price;
    }

    public Timestamp calcTime() {
        Timestamp newEndTime = null;
        return newEndTime;
    }

}
