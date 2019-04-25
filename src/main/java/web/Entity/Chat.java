package web.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Chat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long seller_id;
    private long receiver_id;
    private String message = null;

    public Chat() {

    }

    public long getId() {
        return id;
    }

    public long getSeller_id() {
        return seller_id;
    }

    public long getReceiver_id() {
        return receiver_id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSeller_id(long seller_id) {
        this.seller_id = seller_id;
    }

    public void setReceiver_id(long receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
