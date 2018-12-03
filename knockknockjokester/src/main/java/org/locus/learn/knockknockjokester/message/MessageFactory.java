package org.locus.learn.knockknockjokester.message;

import knock.model.JokeStage;
import knock.model.service.JokeService;
import org.locus.learn.knockknockjokester.model.Joke;
import org.locus.learn.knockknockjokester.service.JokesHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {

    private JokeService jokeService;

    private JokesHolder jokesHolder;

    private String name;

    private String delimiter;

    @Autowired
    public MessageFactory(final JokeService jokeService,
                          final JokesHolder jokesHolder) {
        this.jokeService = jokeService;
        this.jokesHolder = jokesHolder;

        name = "Jokester";
        delimiter = jokeService.getDelimiter();
    }

    public String createMessage(String oldMessage, JokeStage stage) {

        StringBuilder builder = new StringBuilder();
        builder.append(stage);

        builder.append(delimiter);

        if (JokeStage.PUNCHLINE.equals(stage)) {

            Long jokeId = jokeService.getJokeId(oldMessage);
            builder.append(jokeId);

            builder.append(delimiter);
            builder.append(jokeService.getJokeBody(oldMessage));
            builder.append("\n");
            builder.append(this.name);
            builder.append(": ");
            builder.append(jokesHolder.getJokePunchline(jokeId));

        } else if (JokeStage.SETUP.equals(stage)) {

            Joke joke = jokesHolder.getRandomJoke();
            builder.append(joke.getId());
            builder.append(delimiter);
            builder.append(jokeService.getJokeBody(oldMessage));
            builder.append("\n");
            builder.append(this.name);
            builder.append(": ");
            builder.append(joke.getFirst_line());

        } else if (JokeStage.KNOCK_KNOCK.equals(stage)) {

            builder.append("empty");
            builder.append(delimiter);
            builder.append(this.name);
            builder.append(": Knock Knock");

        }

        return builder.toString();
    }

}
