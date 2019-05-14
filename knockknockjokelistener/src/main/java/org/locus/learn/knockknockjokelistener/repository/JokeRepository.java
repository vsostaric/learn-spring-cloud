package org.locus.learn.knockknockjokelistener.repository;

import org.locus.learn.knockknockjokelistener.repository.entities.JokeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JokeRepository extends MongoRepository<JokeEntity, String> {

}
