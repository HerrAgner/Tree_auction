package web;

import javax.persistence.*;

@Entity
@Table(name = "auctions")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private final String title = null;
    private final String description = null;

    public Post() {

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
}
