package web.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String title;
    public String description;
    public int startingBid;
    // etc

    @ManyToOne
    public User seller;

    public Auction() {
    }

}
