package org.locus.learn.knockknockjokelistener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import knock.model.service.JokeExchanger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KnockknockjokelistenerApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context =
                SpringApplication.run(KnockknockjokelistenerApplication.class, args);

        JokeExchanger jokeListener = context.getBean(JokeExchanger.class);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(jokeListener::getMessage, 1, 2, TimeUnit.SECONDS);


    }
}
