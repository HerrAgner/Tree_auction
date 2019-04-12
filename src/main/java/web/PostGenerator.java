package web;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PostGenerator {

    @Bean
    public Post createWorld() {
        return new Post();
    }
}
