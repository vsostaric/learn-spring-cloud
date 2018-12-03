package knock.model.service.impl;

import knock.model.JokeStage;
import knock.model.service.JokeService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class JokeServiceImpl implements JokeService {

    public String getDelimiter() {
        return ";;;";
    }

    public JokeStage getJokeStage(String message) {

        Optional<String> stageName = getJokePart(message, 0);

        if (stageName.isEmpty()) {
            return JokeStage.INVALID;
        }

        return JokeStage.valueOf(stageName.get());
    }

    @Override
    public Long getJokeId(String message) {
        return Long.valueOf(getJokePart(message, 1).orElse(""));
    }

    public String getJokeBody(String message) {
        return getJokePart(message, 2).orElse("");
    }

    private Optional<String> getJokePart(String message, int messagePart) {
        if (StringUtils.isEmpty(message)) {
            return Optional.empty();
        }

        String[] parts = message.split(getDelimiter());

        if (CollectionUtils.isEmpty(CollectionUtils.arrayToList(parts)) ||
                parts.length < messagePart + 1) {
            return Optional.empty();
        }

        return Optional.of(parts[messagePart]);

    }

}
