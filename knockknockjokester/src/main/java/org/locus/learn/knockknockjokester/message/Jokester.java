package org.locus.learn.knockknockjokester.message;

import knock.model.JokeStage;
import knock.model.service.JokeExchanger;
import knock.model.service.JokeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Jokester implements JokeExchanger {

    private RabbitTemplate rabbitTemplate;

    private JokeService jokeService;

    private MessageFactory messageFactory;

    @Autowired
    public Jokester(final RabbitTemplate rabbitTemplate,
                    final JokeService jokeService,
                    final MessageFactory messageFactory) {
        this.rabbitTemplate = rabbitTemplate;
        this.jokeService = jokeService;
        this.messageFactory = messageFactory;

    }

    public void sendMessage(String message) {

        final String messageToSend =
                messageFactory.createMessage("", JokeStage.KNOCK_KNOCK);
        rabbitTemplate.convertAndSend(messageToSend);
    }

    public void getMessage() {

        String message = (String) rabbitTemplate.receiveAndConvert();

        JokeStage stage = jokeService.getJokeStage(message);

        String responseMessage = "";
        if (JokeStage.WHO_THERE.equals(stage)) {
            responseMessage =
                    messageFactory.createMessage(message, JokeStage.SETUP);
        } else if (JokeStage.SETUP_WHO.equals(stage)) {
            responseMessage =
                    messageFactory.createMessage(message, JokeStage.PUNCHLINE);
        }

        if (!StringUtils.isEmpty(responseMessage)) {
            sendMessage(responseMessage);
        }

    }

}
