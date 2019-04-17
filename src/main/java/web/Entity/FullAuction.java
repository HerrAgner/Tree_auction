package web.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FullAuction extends Auction{

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Image> images = new ArrayList<>();

    public FullAuction() {}
}
