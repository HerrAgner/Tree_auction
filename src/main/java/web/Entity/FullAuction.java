package web.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="auctions")
public class FullAuction {
    private final String title = null;
    private final String description = null;
    private final Timestamp end_time = null;
    private final Timestamp added_time = null;
    private final String seller_id = null;
    private final Float start_price = null;

    @OneToMany(targetEntity = Bid.class, mappedBy = "auction_id", orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Bid> bids = null;

    //private List<String>  images = null;

   // private final String firstname = null;
   // private final String lastname = null;
   // private final String phone = null;


    public FullAuction() {

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

    public Float getStart_price() {
        return start_price;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    /*
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }
    */

}
