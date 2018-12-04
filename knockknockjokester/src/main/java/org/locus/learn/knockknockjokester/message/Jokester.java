package org.locus.learn.knockknockjokester.message;

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

        String messageToSend;
        if (StringUtils.isEmpty(message)) {
            log.info("Starting a joke!");
            messageToSend = messageFactory.createMessage("",
                    JokeStage.KNOCK_KNOCK);
        } else {
            messageToSend = message;
        }
        log.info("Sending: " + messageToSend);
        rabbitTemplate.convertAndSend("jokester.send.queue", messageToSend);
    }

    public void getMessage() {

        String message = (String) rabbitTemplate.receiveAndConvert("jokester.receive.queue");
        log.info("Got message: " + message);

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
