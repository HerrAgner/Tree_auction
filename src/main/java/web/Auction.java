package web;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private final String title = null;
    private final String description = null;
    private final Timestamp end_time = null;
    private final Timestamp added_time = null;
    private final String seller_id = null;
    private final Float start_price = null;

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
}
