package web.InnitDB;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import web.Entity.Auction;
import web.Entity.Picture;
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

        User user01 = new User();
        user01.setPassword(encoder.encode("22"));
        user01.setEmail("ali.albadri77@gmail.com");
        user01.setFirstName("ali");
        user01.setLastName("Albadri");
        user01.setPhoneNumber("070 045 - 5645");
        UserRepo.save(user01);

        User user0 = new User();
        user0.setPassword(encoder.encode("22"));
        user0.setEmail("rami.albadri77@gmail.com");
        user0.setFirstName("Rami");
        user0.setLastName("Albadri");
        user0.setPhoneNumber("070 045 - 5645");
        UserRepo.save(user0);

        User user1 = new User();
        user1.setPassword(encoder.encode("22"));
        user1.setEmail("john.doe@gmail.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPhoneNumber("(127)-963-1879");
        UserRepo.save(user1);

        User user2 = new User();
        user2.setPassword(encoder.encode("1"));
        user2.setEmail("eric.rl@me.com");
        user2.setFirstName("Eric");
        user2.setLastName("Rasmusson");
        user2.setPhoneNumber("(245)-965-1079");
        UserRepo.save(user2);

        User user3 = new User();
        user3.setPassword(encoder.encode("123"));
        user3.setEmail("ted@gmail.com");
        user3.setFirstName("Ted");
        user3.setLastName("Agner");
        user3.setPhoneNumber("(205)-165-1049");
        UserRepo.save(user3);


        Auction auction1 = new Auction();
        LocalDateTime ldt1 = LocalDateTime.of(2019,5, 10, 10, 30);
        auction1.setTitle("Super-sonic boat for sale!");
        auction1.setDescription("Worlds fastest boat is up for grabbs!");
        auction1.setAddedTime(Timestamp.from(Instant.now()));
        auction1.setEnd_time(Timestamp.valueOf(ldt1));
        auction1.setSeller_id("john.doe@gmail.com");
        auction1.setStart_price((float) 200);
        auction1.setImage("boat.jpg");
        AuctionRepo.save(auction1);

        Auction auction2 = new Auction();
        LocalDateTime ldt2 = LocalDateTime.of(2019,5, 29, 10, 30);
        auction2.setTitle("Perfect family car");
        auction2.setDescription("This car is alot bigger than your would first expect. Perfect for a family of twelve");
        auction2.setAddedTime(Timestamp.from(Instant.now()));
        auction2.setEnd_time(Timestamp.valueOf(ldt2));
        auction2.setSeller_id("eric.rl@me.com");
        auction2.setStart_price((float) 1004.03);
        auction2.setImage("car.jpg");
        AuctionRepo.save(auction2);

        Picture picture1 = new Picture();
        picture1.setAuctionId(2);
        picture1.setPicture("carInside");

        Auction auction3 = new Auction();
        LocalDateTime ldt3 = LocalDateTime.of(2019,5, 15, 10, 30);
        auction3.setTitle("Super cool bike");
        auction3.setDescription("Worlds fastest bike is up for grabbs!");
        auction3.setAddedTime(Timestamp.from(Instant.now()));
        auction3.setEnd_time(Timestamp.valueOf(ldt3));
        auction3.setSeller_id("ted@gmail.com");
        auction3.setStart_price((float) 5032.0);
        auction3.setImage("ChildrensBike.jpg");
        AuctionRepo.save(auction3);

        Auction auction4 = new Auction();
        LocalDateTime ldt4 = LocalDateTime.of(2019,5, 3, 12, 30);
        auction4.setTitle("average car!");
        auction4.setDescription("Just an average car !");
        auction4.setAddedTime(Timestamp.from(Instant.now()));
        auction4.setEnd_time(Timestamp.valueOf(ldt4));
        auction4.setSeller_id("eric.rl@me.com");
        auction4.setStart_price((float) 10);
        auction4.setImage("normalcarwow.jpg");
        AuctionRepo.save(auction4);

        Auction auction5 = new Auction();
        LocalDateTime ldt5 = LocalDateTime.of(2019,5, 25, 12, 30);
        auction5.setTitle("Very comfy chair");
        auction5.setDescription("Classic ikea chair");
        auction5.setAddedTime(Timestamp.from(Instant.now()));
        auction5.setEnd_time(Timestamp.valueOf(ldt5));
        auction5.setSeller_id("eric.rl@me.com");
        auction5.setStart_price((float) 32.0);
        auction5.setImage("chair.jpg");
        AuctionRepo.save(auction5);

        Auction auction6 = new Auction();
        LocalDateTime ldt6 = LocalDateTime.of(2019,5, 12, 2, 30);
        auction6.setTitle("a plant.");
        auction6.setDescription("Got this for my 25th birthday so it holds some sentimental value. Its also nice to look at");
        auction6.setAddedTime(Timestamp.from(Instant.now()));
        auction6.setEnd_time(Timestamp.valueOf(ldt6));
        auction6.setSeller_id("eric.rl@me.com");
        auction6.setStart_price((float) 1000.0);
        auction6.setImage("green.jpg");
        AuctionRepo.save(auction6);

        Auction auction7 = new Auction();
        LocalDateTime ldt7 = LocalDateTime.of(2019,5, 13, 5, 30);
        auction7.setTitle("phone/murder weapon");
        auction7.setDescription("Use it as u like");
        auction7.setAddedTime(Timestamp.from(Instant.now()));
        auction7.setEnd_time(Timestamp.valueOf(ldt7));
        auction7.setSeller_id("eric.rl@me.com");
        auction7.setStart_price((float) 150.0);
        auction7.setImage("phone.jpg");
        AuctionRepo.save(auction7);

        Auction auction8 = new Auction();
        LocalDateTime ldt8 = LocalDateTime.of(2019,5, 10, 10, 30);
        auction8.setTitle("500 googleplex yottabytes of storage");
        auction8.setDescription("If u want more storage for games n stuff this is perfect for you!");
        auction8.setAddedTime(Timestamp.from(Instant.now()));
        auction8.setEnd_time(Timestamp.valueOf(ldt8));
        auction8.setSeller_id("eric.rl@me.com");
        auction8.setStart_price((float) 250.0);
        auction8.setImage("computer.jpg");
        AuctionRepo.save(auction8);

        Auction auction9 = new Auction();
        LocalDateTime ldt9 = LocalDateTime.of(2019,5, 10, 12, 30);
        auction9.setTitle("The number 5");
        auction9.setDescription("Selling the number 5, dont like it anymore");
        auction9.setAddedTime(Timestamp.from(Instant.now()));
        auction9.setEnd_time(Timestamp.valueOf(ldt9));
        auction9.setSeller_id("eric.rl@me.com");
        auction9.setStart_price((float) 350.0);
        auction9.setImage("five.jpg");
        AuctionRepo.save(auction9);

        Auction auction10 = new Auction();
        LocalDateTime ldt10 = LocalDateTime.of(2019,5, 7, 8, 30);
        auction10.setTitle("Very fast bike, much wow!");
        auction10.setDescription("Worlds fastest bike is up for grabbs!");
        auction10.setAddedTime(Timestamp.from(Instant.now()));
        auction10.setEnd_time(Timestamp.valueOf(ldt10));
        auction10.setSeller_id("eric.rl@me.com");
        auction10.setStart_price((float) 450.0);
        auction10.setImage("Example.jpg");
        AuctionRepo.save(auction10);

        Auction auction11 = new Auction();
        LocalDateTime ldt11 = LocalDateTime.of(2019,5, 8, 5, 30);
        auction11.setTitle("Very fast bike, much wow!");
        auction11.setDescription("Worlds fastest bike is up for grabbs!");
        auction11.setAddedTime(Timestamp.from(Instant.now()));
        auction11.setEnd_time(Timestamp.valueOf(ldt11));
        auction11.setSeller_id("eric.rl@me.com");
        auction11.setStart_price((float) 550.0);
        auction11.setImage("Example.jpg");
        AuctionRepo.save(auction11);

        Auction auction12 = new Auction();
        LocalDateTime ldt12 = LocalDateTime.of(2019,4, 23, 5, 30);
        auction12.setTitle("utgången aktion jao, much wow!");
        auction12.setDescription("utgången aktion jao");
        auction12.setAddedTime(Timestamp.from(Instant.now()));
        auction12.setEnd_time(Timestamp.valueOf(ldt12));
        auction12.setSeller_id("eric.rl@me.com");
        auction12.setStart_price((float) 520.0);
        auction12.setImage("Example.jpg");
        AuctionRepo.save(auction12);

        Auction auction13 = new Auction();
        LocalDateTime ldt13 = LocalDateTime.of(2019,4, 28, 5, 30);
        auction13.setTitle("wow! utgången aktion much wow!");
        auction13.setDescription("Worlds fastest bike is up for grabbs!");
        auction13.setAddedTime(Timestamp.from(Instant.now()));
        auction13.setEnd_time(Timestamp.valueOf(ldt13));
        auction13.setSeller_id("ted@gmail.com");
        auction13.setStart_price((float) 33.0);
        auction13.setImage("Example.jpg");
        AuctionRepo.save(auction13);

        Auction auction14 = new Auction();
        LocalDateTime ldt14 = LocalDateTime.of(2019,4, 28, 9, 30);
        auction14.setTitle(" much wow! utgången aktion jao");
        auction14.setDescription("Worlds fastest bike is up for grabbs!");
        auction14.setAddedTime(Timestamp.from(Instant.now()));
        auction14.setEnd_time(Timestamp.valueOf(ldt14));
        auction14.setSeller_id("john.doe@gmail.com");
        auction14.setStart_price((float) 11.0);
        auction14.setImage("Example.jpg");
        AuctionRepo.save(auction14);

    }
}

