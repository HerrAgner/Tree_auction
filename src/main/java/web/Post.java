package web;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private final String name = null;
    private final Integer populationInMillions = null;

    public Post() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPopulationInMillions() {
        return populationInMillions;
    }

}
