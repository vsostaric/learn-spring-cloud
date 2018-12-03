package org.locus.learn.knockknockjokester.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.locus.learn.knockknockjokester.model.Joke;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class JokesHolder {

    private List<Joke> jokes;

    private Gson gson;

    public JokesHolder() {
        gson = new GsonBuilder().create();
        this.jokes = Arrays.stream(readJokes()).collect(Collectors.toList());
    }

    public String getJokePunchline(Long id) {
        return this.jokes.stream()
                .filter(joke -> joke.getId().equals(id))
                .map(joke -> joke.getSecond_line())
                .findFirst().orElse("");
    }

    public Joke getRandomJoke() {

        int index = new Random()
                .ints(0, jokes.size())
                .findAny()
                .getAsInt();

        return jokes.get(index);

    }

    private Joke[] readJokes() {
        return gson.fromJson(readJokeFile(), Joke[].class);
    }

    private String readJokeFile() {
        final StringBuilder json = new StringBuilder();
        String jokeFileLocation = "jokes/jokes.json";

        new BufferedReader
                (new InputStreamReader(
                        ClassLoader.getSystemResourceAsStream(jokeFileLocation)))
                .lines()
                .forEach((line) -> json.append(line));

        return json.toString();
    }
}
