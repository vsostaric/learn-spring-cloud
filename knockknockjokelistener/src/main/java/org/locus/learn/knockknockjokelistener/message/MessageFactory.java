package org.locus.learn.knockknockjokelistener.message;

import knock.model.JokeStage;
import knock.model.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {

    private JokeService jokeService;

    private String name;

    private String delimiter;

    @Autowired
    public MessageFactory(final JokeService jokeService) {

        this.jokeService = jokeService;

        name = "Listener";
        delimiter = jokeService.getDelimiter();
    }

    public String createMessage(String oldMessage, JokeStage stage) {

        StringBuilder builder = new StringBuilder();
        builder.append(stage);
        builder.append(delimiter);

        if (JokeStage.WHO_THERE.equals(stage)) {
            builder.append("empty");
            builder.append(jokeService.getDelimiter());
            builder.append(jokeService.getJokeBody(oldMessage));
            builder.append("\n");
            builder.append(this.name);
            builder.append(": Who's there?");
        } else if (JokeStage.SETUP_WHO.equals(stage)) {

            builder.append(jokeService.getJokeId(oldMessage));
            builder.append(jokeService.getDelimiter());
            builder.append(jokeService.getJokeBody(oldMessage));
            builder.append("\n");
            builder.append(this.name);
            builder.append(": ");
            String jokeSetup = jokeService.getJokeBody(oldMessage)
                    .substring(
                            jokeService.getJokeBody(oldMessage)
                                    .lastIndexOf("Jokester:") + "Jokester:".length()
                    );
            builder.append(jokeSetup);
            builder.append(" who?");
        } else if (JokeStage.PUNCHLINE.equals(stage)) {
            return "";
        }

        return builder.toString();

    }

}
