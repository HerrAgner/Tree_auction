package web.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private final String picture = null;

    private int auction_id;

    public Picture() {

    }

    public String getPicture() {
        return picture;
    }

    public int getAuction_id() {
        return auction_id;
    }

}
