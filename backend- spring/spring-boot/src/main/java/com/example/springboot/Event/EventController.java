package com.example.springboot.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;



@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;


    // CRUD - Create
    @PostMapping("/addEvent")
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        Event savedEvent = eventRepository.save(event);
        return ResponseEntity.ok(savedEvent);
    }


    // CRUD - Read
    @GetMapping("/unauthorized/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return ResponseEntity.ok(events);
    }
    @GetMapping("/events/{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable int eventId) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
                if (existingEvent.isPresent()) {
                    return ResponseEntity.ok(existingEvent.get());
                }
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Cannot find event with given id");
    }
    @GetMapping("/unauthorized/events/name/{name}")
    public ResponseEntity<Event> getEventByName(@PathVariable String name) {
        Optional<Event> existingEvent = eventRepository.getEventByName(name);
        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();
            return ResponseEntity.ok(event);
        }
        return ResponseEntity.ok(null);
    }
    @GetMapping("/events/list")
    public List<Event>getEventsFromList(List<Integer> ids) {
        return eventRepository.getEventsFromList(ids);
    }
    @GetMapping("/events/eventsOrganisedBy/{userId}")
    public List<Event> getEventsByOrganiser(@PathVariable int userId) {
        return eventRepository.getEventsOrganisedByUser(userId);
    }
    @GetMapping("/events/category/{categoryId}")
    public List<Event> getEventsFromCategory(@PathVariable int categoryId) {
        return eventRepository.getEventsFromCategory(categoryId);
    }
    @GetMapping("/events/last")
    public int getLastEventId() {
        return eventRepository.findTopByOrderByIdDesc()
                .map(Event::getId)
                .orElse(null);
    }
    @GetMapping("/events/toAcceptance")
    public ResponseEntity<List<Event>> getEventsToAcceptance() {
        List<Event> events = eventRepository.getEventsToAcceptance();
        return ResponseEntity.ok(events);
    }
    @GetMapping("/events/fromCategories")
    public ResponseEntity<List<Event>> getEventsFromCategories(List<Integer> categoriesIds) {
        List<Integer> tempEventList = new ArrayList<>();
        for(int categoryId: categoriesIds) {
            List<Event> tempList = eventRepository.getEventsFromCategory(categoryId);
            for (Event event: tempList) {
                if (!tempEventList.contains(event.getId()))
                    tempEventList.add(event.getId());
            }
        }
        return ResponseEntity.ok(getEventsFromList(tempEventList));
    }
    @GetMapping("/unauthorized/events/recent")
    public ResponseEntity<List<Event>> getRecentEvents() {
        List<Event> tempList = eventRepository.findAllByOrderByIdDesc();

        List<Event> acceptedEvents = tempList.stream()
                .filter(event -> EventStatus.ACCEPTED.equals(event.getEventStatus()))
                .limit(10)
                .collect(Collectors.toList());

        return ResponseEntity.ok(acceptedEvents);
    }
    @GetMapping("/unauthorized/events/getRandom")
    public ResponseEntity<List<Event>> getRandomEvents() {
        List<Integer> randomIntList = new ArrayList<>();
        Random random = new Random();
        int min = 1;
        int max = getLastEventId();
        int size = 10;
        int searchSize = 15;

        for (int i = 0; i < searchSize; i++) {
            int randomInt = random.nextInt((max - min) + 1) + min;
            if (!randomIntList.contains(randomInt))
                randomIntList.add(randomInt);
            else
                i--;
        }
        List<Integer> firstTenElements = randomIntList.subList(0, Math.min(randomIntList.size(), size));
        return ResponseEntity.ok(getEventsFromList(firstTenElements));
    }


    // CRUD - Update
    @PutMapping("/events/{eventId}")
    public Event updateEvent(@PathVariable int eventId, @RequestBody Event event) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            Event tempEvent = existingEvent.get();
            tempEvent.setName(event.getName());
            tempEvent.setOrganizer(event.getOrganizer());
            tempEvent.setCategoryList(event.getCategoryList());
            tempEvent.setClientList(event.getClientList());
            tempEvent.setOrganizer(event.getOrganizer());
            tempEvent.setDescription(event.getDescription());
            tempEvent.setSize(event.getSize());
            tempEvent.setLocalisation(event.getLocalisation());
            tempEvent.setFree(event.isFree());
            tempEvent.setReservationNecessary(event.isReservationNecessary());
            tempEvent.setAgeGroup(event.getAgeGroup());
            tempEvent.setStartDate(event.getStartDate());
            tempEvent.setEndDate(event.getEndDate());
            tempEvent.setEventStatus(event.getEventStatus());
            tempEvent.setImageUrl(event.getImageUrl());
            return eventRepository.save(tempEvent);
        }
        return new Event();
    }


    // CRUD - Delete
    @DeleteMapping("/events/{eventId}")
    public boolean deleteEvent(@PathVariable int eventId) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }


    @GetMapping("/events/{eventId}/organiser")
    public int getEventOrganiser(@PathVariable int eventId) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            return existingEvent.get().getOrganizer();
        }
        return 0;
    }

    @GetMapping("/events/accept/{eventId}")
    public ResponseEntity<Boolean> acceptEvent(@PathVariable int eventId) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            existingEvent.get().setEventStatus(EventStatus.ACCEPTED);
            eventRepository.save(existingEvent.get());
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }


    public List<Integer> getUsersThatSubscribedToEvent(int eventId) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            return existingEvent.get().getClientList();
        }
        return new ArrayList();
    }


    // Subscription Management
    @GetMapping("/events/{eventId}/subscribedUsers")
    public ResponseEntity<List<Integer>> getSubscribedEvents(@PathVariable int eventId) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            Event tempEvent = existingEvent.get();
            return ResponseEntity.ok(tempEvent.getClientList());
        }
        return ResponseEntity.ok(new ArrayList<>());
    }
    @GetMapping("/events/{eventId}/subscribedCategories")
    public ResponseEntity<List<Integer>> getSubscribedCategories(@PathVariable int eventId) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            Event tempEvent = existingEvent.get();
            return ResponseEntity.ok(tempEvent.getCategoryList());
        }
        return ResponseEntity.ok(new ArrayList<>());
    }
    public ResponseEntity<Boolean> subscribeUser(@PathVariable Integer userId, @PathVariable int eventId) {
        // jest wywoływane przez UserController.subscribeEvent
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            Event tempEvent = existingEvent.get();
            List<Integer> tempList = tempEvent.getClientList();
            if (tempList.contains(userId))
                return ResponseEntity.ok(false);
            tempList.add(userId);
            tempEvent.setClientList(tempList);
            eventRepository.save(tempEvent);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
    @PatchMapping("/events/{eventId}/subscribeCategory/{categoryId}")
    public ResponseEntity<Boolean> subscribeCategory(@PathVariable int eventId, @PathVariable int categoryId) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            Event tempEvent = existingEvent.get();
            List<Integer> tempList = tempEvent.getCategoryList();
            if (tempList.contains(categoryId))
                return ResponseEntity.ok(false);
            tempList.add(categoryId);
            tempEvent.setCategoryList(tempList);
            eventRepository.save(tempEvent);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
    public ResponseEntity<Boolean> unsubscribeUser(@PathVariable Integer userId, @PathVariable int eventId) {
        // jest wywoływane przez UserController.subscribeEvent
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            Event tempEvent = existingEvent.get();
            List<Integer> tempList = tempEvent.getClientList();
            if (!tempList.contains(userId))
                return ResponseEntity.ok(false);
            tempList.remove(userId);
            tempEvent.setClientList(tempList);
            eventRepository.save(tempEvent);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
    @PatchMapping("/events/{eventId}/unsubscribeCategory/{categoryId}")
    public ResponseEntity<Boolean> unsubscribeCategory(@PathVariable int eventId, @PathVariable Integer categoryId) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent()) {
            Event tempEvent = existingEvent.get();
            List<Integer> tempList = tempEvent.getCategoryList();
            if (!tempList.contains(categoryId))
                return ResponseEntity.ok(false);
            tempList.remove(categoryId);
            tempEvent.setCategoryList(tempList);
            eventRepository.save(tempEvent);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }


    @PostMapping("/unauthorized/events/filter")
    public ResponseEntity<List<Event>> getAllFilteredCategories(@RequestBody FilteredEventParameters filteredEventParameters)
    {
        String name = filteredEventParameters.getName();
        List<Integer> categoryList = filteredEventParameters.getCategoryList();
        String localisation = filteredEventParameters.getLocalisation();
        String startDate = filteredEventParameters.getStartDate();
        String endDate = filteredEventParameters.getEndDate();
        Boolean isFinished = filteredEventParameters.getIsFinished();
        Integer reservation = filteredEventParameters.getReservation();
        Integer isFree = filteredEventParameters.getIsFree();
        AgeGroup ageGroup = filteredEventParameters.getAgeGroup();
        LocalDateTime startDateTime, endDateTime;
        boolean startDateRelevant, endDateRelevant;

        if (startDate.equals("")) {
            startDateTime = LocalDateTime.now();
            startDateRelevant=false;
        }
        else {
            startDateTime = LocalDateTime.parse(startDate);
            startDateRelevant = true;
        }

        if (endDate.equals("")) {
            endDateTime = LocalDateTime.now();
            endDateRelevant=false;
        }
        else {
            endDateTime = LocalDateTime.parse(endDate);
            endDateRelevant = true;
        }

        List<Event> allEvents;
        List<Event> filteredEvents = new ArrayList<>();

        if (categoryList.size() == 0)
            allEvents = getAllEvents().getBody();
        else
            allEvents = getEventsFromCategories(categoryList).getBody();

        for(Event event: allEvents) {
            if (event.getEventStatus() == EventStatus.ACCEPTED || event.getEventStatus() == EventStatus.EDITED)
                if (event.getName().toLowerCase().contains(name.toLowerCase()))
                    if (event.getLocalisation().toLowerCase().contains(localisation.toLowerCase()))
                        if (event.getEndDate().isAfter(LocalDateTime.now()) && !isFinished || event.getEndDate().isBefore(LocalDateTime.now()) && isFinished)
                            if (reservation == 2 || (event.isReservationNecessary() && reservation == 1) || (!event.isReservationNecessary() && reservation == 0))
                                if (isFree == 2 || (event.isFree() && isFree == 1) || (!event.isFree() && isFree == 0))
                                    if (ageGroup == AgeGroup.FAMILY_FRIENDLY && event.getAgeGroup() == AgeGroup.FAMILY_FRIENDLY ||
                                            ageGroup == AgeGroup.OVER12 && (event.getAgeGroup() == AgeGroup.FAMILY_FRIENDLY || event.getAgeGroup() == AgeGroup.OVER12) ||
                                            ageGroup == AgeGroup.OVER16 && (event.getAgeGroup() == AgeGroup.FAMILY_FRIENDLY || event.getAgeGroup() == AgeGroup.OVER12 || event.getAgeGroup() == AgeGroup.OVER16) ||
                                            ageGroup == AgeGroup.OVER18 && (event.getAgeGroup() == AgeGroup.FAMILY_FRIENDLY || event.getAgeGroup() == AgeGroup.OVER12 || event.getAgeGroup() == AgeGroup.OVER16 || event.getAgeGroup() == AgeGroup.OVER18))

                                        if (!startDateRelevant || startDateTime.isBefore(event.getStartDate()))
                                            if (!endDateRelevant || endDateTime.isAfter(event.getEndDate()))
                                                filteredEvents.add(event);

        }

        return ResponseEntity.ok(filteredEvents);
    }
}
