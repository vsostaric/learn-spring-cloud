package org.locus.learn.knockknockjokelistener.message;

import knock.model.JokeStage;
import knock.model.service.JokeExchanger;
import knock.model.service.JokeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JokeListener implements JokeExchanger {

    private RabbitTemplate rabbitTemplate;

    private JokeService jokeService;

    private MessageFactory messageFactory;

    @Autowired
    public JokeListener(
            final RabbitTemplate rabbitTemplate,
            final JokeService jokeService,
            final MessageFactory messageFactory
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.jokeService = jokeService;
        this.messageFactory = messageFactory;

    }

    @Override
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(message);
    }

    public void getMessage() {
        String message = (String) rabbitTemplate.receiveAndConvert();
        JokeStage stage = jokeService.getJokeStage(message);

        log.info("Got message: " + message);

        String responseMessage = "";
        if (JokeStage.KNOCK_KNOCK.equals(stage)) {
            responseMessage =
                    messageFactory.createMessage(message, JokeStage.WHO_THERE);
        } else if (JokeStage.SETUP.equals(stage)) {
            responseMessage =
                    messageFactory.createMessage(message, JokeStage.SETUP_WHO);
        } else if (JokeStage.PUNCHLINE.equals(stage)) {
            log.info("Joke over! " + message);
            System.out.println(message);
        }

        if (!StringUtils.isEmpty(responseMessage)) {
            log.info("Sending: " + responseMessage);
            sendMessage(responseMessage);
        }

    }

}
