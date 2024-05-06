package com.example.springboot.Event;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {

    //CRUD - Create & Update
    Event save(Event event);


    //CRUD - Read
    List<Event> findAll();
    Optional<Event> findById(int id);
    Optional<Event> findTopByOrderByIdDesc();
    List<Event> findTop10ByOrderByIdDesc();
    List<Event> findAllByOrderByIdDesc();
    @Query("{'name': ?0}")
    Optional<Event> getEventByName(String name);
    @Query("{'id': {'$in': ?0}}")
    List<Event> getEventsFromList(@Param("ids") List<Integer> ids);
    @Query("{'organizer': ?0}")
    List<Event> getEventsOrganisedByUser(@Param("id") int userId);
    @Query("{'categoryList': {'$elemMatch': {'$eq': ?0}}}")
    List<Event> getEventsFromCategory(int id);
    @Query("{'eventStatus': TO_ACCEPTANCE}")
    List<Event> getEventsToAcceptance();
    @Query("{'$or': [{'id': {'$in': ?0}}, {'eventStatus': ACCEPTED}]}")
    List<Event> getAcceptedEventsFromList(@Param("ids") List<Integer> ids);


    // CRUD - Delete
    void deleteById(int id);
}
