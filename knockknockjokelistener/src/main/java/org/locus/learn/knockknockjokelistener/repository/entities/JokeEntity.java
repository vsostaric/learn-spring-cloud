package org.locus.learn.knockknockjokelistener.repository.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class JokeEntity {

    @Id
    private String id;

    private String jokeText;

    public JokeEntity(String jokeText) {
        this.jokeText = jokeText;
        this.id = "joke" +
                LocalDateTime
                        .now()
                        .format(DateTimeFormatter.ofPattern("DDMMYYhhmmssnn"));
    }

    @Override
    public String toString() {
        return jokeText;
    }
}
