package knock.model.service;

import knock.model.JokeStage;

public interface JokeService {

    String getDelimiter();

    JokeStage getJokeStage(String message);

    Long getJokeId(String message);

    String getJokeBody(String message);

}
