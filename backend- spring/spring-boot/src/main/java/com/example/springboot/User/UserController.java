package com.example.springboot.User;

import com.example.springboot.Category.Category;
import com.example.springboot.Category.CategoryController;
import com.example.springboot.Event.Event;
import com.example.springboot.Event.EventController;
import com.example.springboot.Event.EventStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventController eventController;
    @Autowired
    private CategoryController categoryController;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    // CRUD - Create
    @PostMapping("/unauthorized/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.getUserByNameOrMail(user.getName());
        Optional<User> existingUser2 = userRepository.getUserByNameOrMail(user.getMail());
        if (existingUser.isPresent() || existingUser2.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("User with the same name or email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }


    // CRUD - Read
    @GetMapping("users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(UserDTO::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
    @GetMapping("/users/filter")
    public ResponseEntity<List<UserDTO>> getAllUsersFiltered(
        @RequestParam(required = false, defaultValue = "") String name,
        @RequestParam(required = false, defaultValue = "") String mail,
        @RequestParam boolean admin,
        @RequestParam boolean mod,
        @RequestParam boolean ver,
        @RequestParam boolean nonver
        )
    {
        List<User> users = userRepository.findAll();
        List<User> filteredUsers = new ArrayList<>();
        for(User user: users) {
            int tot = 0;
            if (user.getName().toLowerCase().contains(name.toLowerCase()))
                tot++;
            if (user.getMail().toLowerCase().contains(mail.toLowerCase()))
                tot++;
            if (user.getPermissionLevel() == PermissionLevel.ADMIN && admin)
                tot++;
            if (user.getPermissionLevel() == PermissionLevel.MODERATOR && mod)
                tot++;
            if (user.getPermissionLevel() == PermissionLevel.VERIFIED_USER && ver)
                tot++;
            if (user.getPermissionLevel() == PermissionLevel.UNVERIFIED_USER && nonver)
                tot++;
            if (tot == 3)
                filteredUsers.add(user);
        }
        List<UserDTO> userDTOs = filteredUsers.stream()
                .map(UserDTO::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        Optional<User> existingUser = userRepository.getUserById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            return ResponseEntity.ok(UserDTO.toDTO(user));
        }
        return ResponseEntity.ok(null);
    }
    @GetMapping("/users/name/{nameOrMail}")
    public UserDTO getUserByName(@PathVariable String nameOrMail) {
        Optional<User> existingUser = userRepository.getUserByNameOrMail(nameOrMail);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            return UserDTO.toDTO(user);
        }
        return null;
    }
    @GetMapping("/users/list")
    public List<User> getUsersFromList(ArrayList<Integer> idList) {
        return userRepository.getUsersFromList(idList);
    }
    @GetMapping("/unauthorized/user/last")
    public int getLastUser() {
        return userRepository.findTopByOrderByIdDesc()
                .map(User::getId)
                .orElse(1);
    }


    // CRUD - Update
    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable int userId, @RequestBody User user) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User tempUser = existingUser.get();
            tempUser.setMail(user.getMail());
            tempUser.setName(user.getName());
            tempUser.setPassword(user.getPassword());
            tempUser.setPermissionLevel(user.getPermissionLevel());
            tempUser.setSubscribedEvents(user.getSubscribedEvents());
            tempUser.setSubscribedCategories(user.getSubscribedCategories());
            return userRepository.save(tempUser);
        }
        user.setId(userId);
        return userRepository.save(user);
    }
    @PutMapping("/users/{userId}/updatePermissions")
    public User updateUserPermissions(@PathVariable Integer userId, @RequestBody Integer permissionLevel) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User tempUser = existingUser.get();
            switch (permissionLevel) {
                case 4 -> tempUser.setPermissionLevel(PermissionLevel.UNVERIFIED_USER);
                case 3 -> tempUser.setPermissionLevel(PermissionLevel.VERIFIED_USER);
                case 2 -> tempUser.setPermissionLevel(PermissionLevel.MODERATOR);
                case 1 -> tempUser.setPermissionLevel(PermissionLevel.ADMIN);
                default -> {
                }
            }
            return userRepository.save(tempUser);
        }
        return null;
    }

    @PutMapping("/users/{userId}/updatePassword")
    public User updateUserPassword(@PathVariable Integer userId, @RequestBody String password) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User tempUser = existingUser.get();
            tempUser.setPassword(passwordEncoder.encode(password));
            return userRepository.save(tempUser);
        }
        return null;
    }


    // CRUD - Delete
    @DeleteMapping("/users/{userId}")
    public boolean deleteUser(@PathVariable int userId) {
        Optional<User> existingUser = userRepository.getUserById(userId);
        if (existingUser.isPresent()) {
            User tempUser = existingUser.get();
            List<Event> tempList = eventController.getEventsByOrganiser(userId);
            for (Event event : tempList) {
                deleteEvent(userId, event.getId());
            }

            List<Integer> subscribedEvents = tempUser.getSubscribedEvents();
            for (Integer eventId : subscribedEvents) {
                unsubscribeEvent(userId, eventId);
            }

            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


    // Account management
    @GetMapping("users/login")
    public ResponseEntity<Boolean> tryToLoginUser(String loginOrMail, String password) {
        boolean result = userRepository.login(loginOrMail, password).isPresent();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/users/{userId}/getPermissionLevel")
    public ResponseEntity<PermissionLevel> getPermissionLevel(@PathVariable int userId) {
        Optional<User> existingUser = userRepository.getUserById(userId);
        return existingUser.map(user -> ResponseEntity.ok(user.getPermissionLevel()))
                .orElseGet(() -> ResponseEntity.ok(PermissionLevel.UNVERIFIED_USER));
    }
    @PutMapping("/users/{userId}/changePermissions")
    public ResponseEntity<Boolean> changePermissionLevel(@PathVariable int userId, @RequestBody PermissionLevel permissionLevel) {
        userRepository.findById(userId).map(user -> {
                    user.setPermissionLevel(permissionLevel);
        return ResponseEntity.ok(true);});
        return ResponseEntity.ok(false);
    }
    @GetMapping("/unauthorized/users/{userId}/getName")
    public ResponseEntity<String> getUsersName(@PathVariable int userId) {
        Optional<User> existingUser = userRepository.getUserById(userId);
        if (existingUser.isPresent()) {
            User tempUser = existingUser.get();
            return ResponseEntity.ok(tempUser.getName());
        }
        return ResponseEntity.ok("");
    }


    // Subscription Management
    @GetMapping("/users/{userId}/subscribedEvents")
    public ResponseEntity<List<Event>> getSubscribedEvents(@PathVariable int userId) {
        Optional<User> existingUser = userRepository.getUserById(userId);
        if (existingUser.isPresent()) {
            User tempUser = existingUser.get();
            return ResponseEntity.ok(eventController.getEventsFromList(tempUser.getSubscribedEvents()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }
    @GetMapping("/users/{userId}/subscribedCategories")
    public ResponseEntity<List<Category>> getSubscribedCategories(@PathVariable int userId) {
        Optional<User> existingUser = userRepository.getUserById(userId);
        if (existingUser.isPresent()) {
            User tempUser = existingUser.get();
            return ResponseEntity.ok(categoryController.getCategoriesFromList(tempUser.getSubscribedCategories()));
        }
        return ResponseEntity.ok(new ArrayList<>());
    }
    @PatchMapping("/users/{userId}/subscribeEvent/{eventId}")
    public ResponseEntity<Boolean> subscribeEvent(@PathVariable int userId, @PathVariable int eventId) {
        Optional<User> existingUser = userRepository.getUserById(userId);
        if (existingUser.isPresent()) {
            User tempUser = existingUser.get();
            List<Integer> tempList = tempUser.getSubscribedEvents();
            if (tempList.contains(eventId))
                return ResponseEntity.ok(false);
            tempList.add(eventId);
            tempUser.setSubscribedEvents(tempList);
            userRepository.save(tempUser);
            eventController.subscribeUser(userId, eventId);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
    @PatchMapping("/users/{userId}/subscribeCategory/{categoryId}")
    public ResponseEntity<Boolean> subscribeCategory(@PathVariable int userId, @PathVariable int categoryId) {
        Optional<User> existingUser = userRepository.getUserById(userId);
        if (existingUser.isPresent()) {
            User tempUser = existingUser.get();
            List<Integer> tempList = tempUser.getSubscribedCategories();
            if (tempList.contains(categoryId))
                return ResponseEntity.ok(false);
            tempList.add(categoryId);
            tempUser.setSubscribedCategories(tempList);
            userRepository.save(tempUser);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
    @PatchMapping("/users/{userId}/subscribeCategories")
    public ResponseEntity<Boolean> subscribeCategories(@PathVariable int userId, @RequestBody List<Integer> categoryList) {
        Optional<User> existingUser = userRepository.getUserById(userId);
        if (existingUser.isPresent()) {
            User tempUser = existingUser.get();
            List<Integer> tempList = tempUser.getSubscribedCategories();

            for(Integer categoryId: categoryList)
                if (!tempList.contains(categoryId))
                    tempList.add(categoryId);

            tempUser.setSubscribedCategories(tempList);
            userRepository.save(tempUser);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);

    }
    @PatchMapping("/users/{userId}/unsubscribeEvent/{eventId}")
    public ResponseEntity<Boolean> unsubscribeEvent(@PathVariable int userId, @PathVariable Integer eventId) {
        Optional<User> existingUser = userRepository.getUserById(userId);
        if (existingUser.isPresent()) {
            if (eventController.getEventOrganiser(eventId) == userId)
                return ResponseEntity.ok(false);
            User tempUser = existingUser.get();
            List<Integer> tempList = tempUser.getSubscribedEvents();
            if (!tempList.contains(eventId))
                return ResponseEntity.ok(false);
            tempList.remove(eventId);
            tempUser.setSubscribedEvents(tempList);
            userRepository.save(tempUser);
            eventController.unsubscribeUser(userId, eventId);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
    @PatchMapping("/users/{userId}/unsubscribeCategory/{categoryId}")
    public ResponseEntity<Boolean> unsubscribeCategory(@PathVariable int userId, @PathVariable Integer categoryId) {
        Optional<User> existingUser = userRepository.getUserById(userId);
        if (existingUser.isPresent()) {
            User tempUser = existingUser.get();
            List<Integer> tempList = tempUser.getSubscribedCategories();
            if (!tempList.contains(categoryId))
                return ResponseEntity.ok(false);
            tempList.remove(categoryId);
            tempUser.setSubscribedCategories(tempList);
            userRepository.save(tempUser);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }


    // User's Events
    @PostMapping("/users/{userId}/createEvent")
    public ResponseEntity<Event> createEvent(@PathVariable int userId, @RequestBody Event event) {
        ResponseEntity<Event> tempEvent = eventController.addEvent(event);
        subscribeEvent(userId, event.getId());
        return tempEvent;
    }
    @PatchMapping("/users/{userId}/deleteEvent/{eventId}")
    public ResponseEntity<Boolean> deleteEvent(@PathVariable int userId, @PathVariable Integer eventId) {
        if (eventController.getEventOrganiser(eventId) != userId)
            return ResponseEntity.ok(false);
        List<Integer> userList = eventController.getUsersThatSubscribedToEvent(eventId);
        for(Integer user:userList) {
            unsubscribeEvent(user, eventId);
        }

        eventController.deleteEvent(eventId);

        Optional<User> exitingUser = userRepository.findById(userId);
        if (exitingUser.isPresent()) {
            User tempUser = exitingUser.get();
            List<Integer> tempList = tempUser.getSubscribedEvents();
            tempList.remove(eventId);
            tempUser.setSubscribedEvents(tempList);
            userRepository.save(tempUser);
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);

    }
    @GetMapping("users/{userId}/myEvents")
    public ResponseEntity<List<Event>> getEventsOrganisedByUser(@PathVariable int userId) {
        return ResponseEntity.ok(eventController.getEventsByOrganiser(userId));
    }
    @PatchMapping("/users/{userId}/updateEvent")
    public ResponseEntity<?> updateEvent(@PathVariable int userId, @RequestBody Event event) {
        if (eventController.getEventOrganiser(event.getId()) != userId)
            return ResponseEntity.ok(false);
        Optional<User> existingUser = userRepository.getUserById(userId);
        if (existingUser.isPresent()) {
            switch (existingUser.get().getPermissionLevel()){
                case UNVERIFIED_USER:
                    event.setId(-event.getId());
                    event.setEventStatus(EventStatus.TO_ACCEPTANCE);
                    event.setOrganizer(0);
                    createEvent(0, event);
                    return ResponseEntity.ok(true);
                default:
                    return ResponseEntity.ok(eventController.updateEvent(event.getId(), event));
            }
        }
        return ResponseEntity.ok(false);
    }


    // Moderator options
    @PostMapping("/users/{userId}/createCategory")
    public ResponseEntity<?> createCategory(@PathVariable int userId, @RequestBody Category category) {
        if (getPermissionLevel(userId).getBody() == PermissionLevel.MODERATOR || getPermissionLevel(userId).getBody() == PermissionLevel.ADMIN)
            return ResponseEntity.ok(categoryController.addCategory(category));
        return ResponseEntity.ok(null);
    }
    @PostMapping("/users/{userId}/createSubCategory")
    public ResponseEntity<?> createSubCategory(@PathVariable int userId, @RequestBody Category subCategory) {
        if (getPermissionLevel(userId).getBody() == PermissionLevel.MODERATOR || getPermissionLevel(userId).getBody() == PermissionLevel.ADMIN)
            return ResponseEntity.ok(categoryController.addSubCategory(subCategory));
        return ResponseEntity.ok(null);
    }
    @PatchMapping("/users/{userId}/updateCategory")
    public ResponseEntity<?> updateCategory(@PathVariable int userId, @RequestBody Category category) {
        if (getPermissionLevel(userId).getBody() == PermissionLevel.MODERATOR || getPermissionLevel(userId).getBody() == PermissionLevel.ADMIN)
            return ResponseEntity.ok(categoryController.updateCategory(category.getId(), category));
        return ResponseEntity.ok(null);
    }

    @PostMapping("/users/{userId}/acceptEvent/{eventId}")
    public ResponseEntity<?> acceptEvent(@PathVariable int userId, @PathVariable Integer eventId) {
        ResponseEntity<?> existingEvent = eventController.getEvent(eventId);
        ResponseEntity<?> existingEvent2 = eventController.getEvent(-eventId);

        if (existingEvent.getBody() instanceof Event && existingEvent2.getBody() instanceof Event) {
            Event originalEvent = (Event) existingEvent.getBody();
            Event copyEvent = (Event) existingEvent2.getBody();

            copyEvent.setOrganizer(originalEvent.getOrganizer());
            copyEvent.setEventStatus(EventStatus.ACCEPTED);
            copyEvent.setClientList(originalEvent.getClientList());
            copyEvent.setId(eventId);

            eventController.deleteEvent(-eventId);
            return ResponseEntity.ok(eventController.updateEvent(eventId, copyEvent));
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Cannot find event with given id");
    }
}
