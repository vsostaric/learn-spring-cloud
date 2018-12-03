package org.locus.learn.knockknockjokelistener.message;

import knock.model.JokeStage;
import knock.model.service.JokeExchanger;
import knock.model.service.JokeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JokeListener implements JokeExchanger {

    private RabbitTemplate rabbitTemplate;

    private JokeService jokeService;

    private String name;

    @Autowired
    public JokeListener(
            final RabbitTemplate rabbitTemplate,
            final JokeService jokeService
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.jokeService = jokeService;

        this.name = "Listener";
    }

    @Override
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(message);
    }

    public void getMessage() {
        String message = (String) rabbitTemplate.receiveAndConvert();

        if (StringUtils.isEmpty(message)) {
            return;
        }

        JokeStage stage = jokeService.getJokeStage(message);

        String responseMessage = "";
        if (JokeStage.KNOCK_KNOCK.equals(stage)) {
            responseMessage = getWhosThereMessage(message);
        } else if (JokeStage.SETUP.equals(stage)) {
            responseMessage = getSetupWhoMessage(message);
        } else if (JokeStage.PUNCHLINE.equals(stage)) {
            System.out.println(message);
        }

        if (!StringUtils.isEmpty(responseMessage)) {
            sendMessage(responseMessage);
        }

    }

    private String getSetupWhoMessage(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append(JokeStage.SETUP_WHO);
        builder.append(jokeService.getDelimiter());
        builder.append(jokeService.getJokeId(message));
        builder.append(jokeService.getDelimiter());
        builder.append(jokeService.getJokeBody(message));
        builder.append("\n");
        builder.append(this.name);
        builder.append(": ");
        builder.append(jokeService.getJokeBody(message));
        builder.append(" who?");
        return builder.toString();
    }

    private String getWhosThereMessage(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append(JokeStage.WHO_THERE);
        builder.append(jokeService.getDelimiter());
        builder.append("empty");
        builder.append(jokeService.getDelimiter());
        builder.append(jokeService.getJokeBody(message));
        builder.append("\n");
        builder.append(this.name);
        builder.append(": Who's there?");
        return builder.toString();
    }

}
