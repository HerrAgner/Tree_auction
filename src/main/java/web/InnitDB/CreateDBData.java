package web.InnitDB;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import web.Entity.Auction;
import web.Entity.User;
import web.Repository.AuctionRepository;
import web.Repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;


@Component
public class CreateDBData implements CommandLineRunner {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository UserRepo;

    @Autowired
    AuctionRepository AuctionRepo;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setPassword(encoder.encode("password1234"));  //passwordet ska crypteras innan det laddas upp till DB
        user1.setEmail("john.doe@gmail.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPhoneNumber("(127)-963-1879");
        UserRepo.save(user1);

        User user2 = new User();
        user2.setPassword(encoder.encode("securePassword"));  //passwordet ska crypteras innan det laddas upp till DB
        user2.setEmail("eric.rl@me.com");
        user2.setFirstName("Eric");
        user2.setLastName("Rasmusson");
        user2.setPhoneNumber("(245)-965-1079");
        UserRepo.save(user2);

        User user3 = new User();
        user3.setPassword(encoder.encode("123"));  //passwordet ska crypteras innan det laddas upp till DB
        user3.setEmail("ted@gmail.com");
        user3.setFirstName("Ted");
        user3.setLastName("Agner");
        user3.setPhoneNumber("(205)-165-1049");
        UserRepo.save(user3);


        Auction auction1 = new Auction();
        LocalDateTime ldt1 = LocalDateTime.of(2019,4, 29, 10, 30);
        auction1.setTitle("Very fast boat, much wow!");
        auction1.setDescription("Worlds fastest boat is up for grabbs!");
        auction1.setAddedTime(Timestamp.from(Instant.now()));
        auction1.setEnd_time(Timestamp.valueOf(ldt1));
        auction1.setSeller_id("john.doe@gmail.com");
        auction1.setStart_price((float) 24.03);
        AuctionRepo.save(auction1);

        Auction auction2 = new Auction();
        LocalDateTime ldt2 = LocalDateTime.of(2019,5, 29, 10, 30);
        auction2.setTitle("Very fast car, much wow!");
        auction2.setDescription("Worlds fastest car is up for grabbs!");
        auction2.setAddedTime(Timestamp.from(Instant.now()));
        auction2.setEnd_time(Timestamp.valueOf(ldt2));
        auction2.setSeller_id("eric.rl@me.com");
        auction2.setStart_price((float) 1004.03);
        AuctionRepo.save(auction2);

        Auction auction3 = new Auction();
        LocalDateTime ldt3 = LocalDateTime.of(2019,4, 26, 10, 30);
        auction3.setTitle("Very fast bike, much wow!");
        auction3.setDescription("Worlds fastest bike is up for grabbs!");
        auction3.setAddedTime(Timestamp.from(Instant.now()));
        auction3.setEnd_time(Timestamp.valueOf(ldt3));
        auction3.setSeller_id("ted@gmail.com");
        auction3.setStart_price((float) 5032.0);
        AuctionRepo.save(auction3);

        Auction auction4 = new Auction();
        LocalDateTime ldt4 = LocalDateTime.of(2019,4, 25, 12, 30);
        auction4.setTitle("Very fast skateboard, much wow!");
        auction4.setDescription("Worlds fastest skateboard is up for grabbs!");
        auction4.setAddedTime(Timestamp.from(Instant.now()));
        auction4.setEnd_time(Timestamp.valueOf(ldt4));
        auction4.setSeller_id("eric.rl@me.com");
        auction4.setStart_price((float) 55432.0);
        AuctionRepo.save(auction4);

        Auction auction5 = new Auction();
        LocalDateTime ldt5 = LocalDateTime.of(2019,4, 29, 12, 30);
        auction5.setTitle("Very fast bike, much wow!");
        auction5.setDescription("Worlds fastest bike is up for grabbs!");
        auction5.setAddedTime(Timestamp.from(Instant.now()));
        auction5.setEnd_time(Timestamp.valueOf(ldt5));
        auction5.setSeller_id("eric.rl@me.com");
        auction5.setStart_price((float) 32.0);
        AuctionRepo.save(auction5);

        Auction auction6 = new Auction();
        LocalDateTime ldt6 = LocalDateTime.of(2019,4, 29, 2, 30);
        auction6.setTitle("Very fast table, much wow!");
        auction6.setDescription("Worlds fastest table is up for grabbs!");
        auction6.setAddedTime(Timestamp.from(Instant.now()));
        auction6.setEnd_time(Timestamp.valueOf(ldt6));
        auction6.setSeller_id("eric.rl@me.com");
        auction6.setStart_price((float) 302.0);
        AuctionRepo.save(auction6);

        Auction auction7 = new Auction();
        LocalDateTime ldt7 = LocalDateTime.of(2019,4, 29, 5, 30);
        auction7.setTitle("Very fast sofa, much wow!");
        auction7.setDescription("Worlds fastest bike is up for grabbs!");
        auction7.setAddedTime(Timestamp.from(Instant.now()));
        auction7.setEnd_time(Timestamp.valueOf(ldt7));
        auction7.setSeller_id("eric.rl@me.com");
        auction7.setStart_price((float) 150.0);
        AuctionRepo.save(auction7);

        Auction auction8 = new Auction();
        LocalDateTime ldt8 = LocalDateTime.of(2019,4, 28, 10, 30);
        auction8.setTitle("Very fast bike, much wow!");
        auction8.setDescription("Worlds fastest sofa is up for grabbs!");
        auction8.setAddedTime(Timestamp.from(Instant.now()));
        auction8.setEnd_time(Timestamp.valueOf(ldt8));
        auction8.setSeller_id("eric.rl@me.com");
        auction8.setStart_price((float) 250.0);
        AuctionRepo.save(auction8);

        Auction auction9 = new Auction();
        LocalDateTime ldt9 = LocalDateTime.of(2019,4, 28, 12, 30);
        auction9.setTitle("Very fast bike, much wow!");
        auction9.setDescription("Worlds fastest bike is up for grabbs!");
        auction9.setAddedTime(Timestamp.from(Instant.now()));
        auction9.setEnd_time(Timestamp.valueOf(ldt9));
        auction9.setSeller_id("eric.rl@me.com");
        auction9.setStart_price((float) 350.0);
        AuctionRepo.save(auction9);

        Auction auction10 = new Auction();
        LocalDateTime ldt10 = LocalDateTime.of(2019,4, 28, 8, 30);
        auction10.setTitle("Very fast bike, much wow!");
        auction10.setDescription("Worlds fastest bike is up for grabbs!");
        auction10.setAddedTime(Timestamp.from(Instant.now()));
        auction10.setEnd_time(Timestamp.valueOf(ldt10));
        auction10.setSeller_id("eric.rl@me.com");
        auction10.setStart_price((float) 450.0);
        AuctionRepo.save(auction10);

        Auction auction11 = new Auction();
        LocalDateTime ldt11 = LocalDateTime.of(2019,4, 28, 5, 30);
        auction11.setTitle("Very fast bike, much wow!");
        auction11.setDescription("Worlds fastest bike is up for grabbs!");
        auction11.setAddedTime(Timestamp.from(Instant.now()));
        auction11.setEnd_time(Timestamp.valueOf(ldt11));
        auction11.setSeller_id("eric.rl@me.com");
        auction11.setStart_price((float) 550.0);
        AuctionRepo.save(auction11);
    }
}

