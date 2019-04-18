package web.Entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private final String picture = null;
    private final int auction_id = 0;

    public Picture() {

    }

    public String getPicture() {
        return picture;
    }

    public int getAuction_id() {
        return auction_id;
    }

}
