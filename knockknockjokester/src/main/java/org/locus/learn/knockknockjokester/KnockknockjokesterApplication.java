package org.locus.learn.knockknockjokester;

import knock.model.service.JokeExchanger;
import org.locus.learn.knockknockjokester.message.Jokester;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class KnockknockjokesterApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KnockknockjokesterApplication.class, args);

        JokeExchanger jokester =
                context.getBean(JokeExchanger.class);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(
                () -> jokester.sendMessage("")
                , 0, 30, TimeUnit.SECONDS);

        executor.scheduleAtFixedRate(
                () -> jokester.getMessage(), 1, 2, TimeUnit.SECONDS);
    }
}
