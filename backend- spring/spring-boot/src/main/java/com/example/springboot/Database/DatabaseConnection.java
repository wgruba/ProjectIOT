/*
package com.example.springboot.Database;

import java.util.*;

import com.example.springboot.Category.Category;
import com.example.springboot.Event.Event;
import com.example.springboot.User.User;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

// TO DO:
//    - system alerts of updates and deletes (system.print - same as CREATE)
//    - sub-category conversions in category version of CRU (DELETE does not interact with it)
//    - throwing and handling Exceptions

*/
/**
 * Contains all operations pertaining to connecting the app with the MongoDB document database.
 *//*

public class DatabaseConnection {
    MongoClient client;
    CodecRegistry pojoCodecRegistry;
    CodecProvider pojoCodecProvider;
    MongoDatabase database;

    */
/**
     * Class constructor, opens a database connection.
     *
     * @param user_name     username of a registered MongoDB database user
     * @param user_password password used to log in the MongoDB database user
     * @param database_name MongoDB database name
     *//*

    public DatabaseConnection(String user_name, String user_password, String database_name) {
        client = connectClient(String.format("mongodb+srv://%s:%s@%s.wwveray.mongodb.net" +
                        "/?retryWrites=true&w=majority",
                user_name, user_password, database_name));
        pojoCodecProvider = PojoCodecProvider
                .builder()
                .register(User.class)
                .register(Event.class)
                .register(Category.class)
                .conventions(Arrays.asList(Conventions.ANNOTATION_CONVENTION))
                .conventions(Arrays.asList(Conventions.CLASS_AND_PROPERTY_CONVENTION))
                .build();
        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        database = client.getDatabase("EventOrganizer").withCodecRegistry(pojoCodecRegistry);
    }

    */
/**
     * Establishes a Client connection with MongoDB.
     *
     * @param connectionString String of Data containing the necessary authorization
     * @return Connected Client
     *//*

    private MongoClient connectClient(String connectionString) {
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(
                new ConnectionString(connectionString)).serverApi(serverApi).build();
        try {
            return MongoClients.create(settings);
        } catch (MongoException e) {
            e.printStackTrace();
            // throw NoClientConnectionException
            return null;
        }
    }


    */
/**
     * Reads User objects from the database matching set criteria.
     *
     * @param amount  amount of objects to be found
     * @param filters set of criteria to be matched
     * @return list of User objects.
     *//*

    public List<User> UserRead(CRUDAmount amount, Bson filters) {
        MongoCollection<User> collection = database.getCollection("User", User.class);
        if (amount == CRUDAmount.SINGLE) {
            ArrayList<User> user_list = new ArrayList<>();
            user_list.add(readUser(filters, collection));
            return user_list;
        } else if (amount == CRUDAmount.MANY) {
            return readUsers(filters, collection);
        } else {
            return null;
        }
    }

    */
/**
     * Reads a User object from the database matching set criteria.
     *
     * @param filters set of criteria to be matched
     * @return list of User objects containing a singular match.
     *//*

    public List<User> UserRead(Bson filters) {
        return UserRead(CRUDAmount.SINGLE, filters);
    }

    */
/**
     * Used to choose the appropriate database connection User functions based on provided data.
     *
     * @param type          type of CUD operation
     * @param amount        amount of documents to affect with the above
     * @param filters       filters used in DELETE and UPDATE operations
     * @param users         users to be added in the CREATE operation
     * @param update_fields update fields to be modified in the UPDATE operation
     * @return boolean value based on success or failure
     *//*

    private boolean UserCUD(CUDType type, CRUDAmount amount, Bson[] filters, User[] users, Bson[] update_fields) {
        MongoCollection<User> collection = database.getCollection("User", User.class);
        if (amount == CRUDAmount.SINGLE) {
            if (type == CUDType.CREATE) {
                if (users != null) {
                    return createUser(users[0], collection);
                }
            } else if (type == CUDType.UPDATE) {
                if (filters != null && update_fields != null) {
                    return updateUser(update_fields[0], filters[0], collection);
                }
            } else if (type == CUDType.DELETE) {
                if (filters != null) {
                    return deleteUser(filters[0], collection);
                }
            }
        } else if (amount == CRUDAmount.MANY) {
            if (type == CUDType.CREATE) {
                if (users != null) {
                    return createUsers(users, collection);
                }
            } else if (type == CUDType.UPDATE) {
                if (update_fields != null && filters != null) {
                    return updateUsers(update_fields, filters, collection);
                }
            } else if (type == CUDType.DELETE) {
                if (filters != null) {
                    return deleteUsers(filters[0], collection);
                }
            }
        }
        return false;
    }

    */
/**
     * Creates a User document in the database
     *
     * @param users array of users, the first position is added to the database
     * @return boolean information describing success or failure
     *//*

    public boolean UserCUD(User[] users) {
        return UserCUD(CUDType.CREATE, CRUDAmount.SINGLE, null, users, null);
    }

    */
/**
     * Deletes a User document from the database
     *
     * @param filters array of filters, the first position is used to find the document to delete
     * @return boolean information describing success or failure
     *//*

    public boolean UserCUD(Bson[] filters) {
        return UserCUD(CUDType.DELETE, CRUDAmount.SINGLE, filters, null, null);
    }

    */
/**
     * Updates a User document in the database
     *
     * @param filters       array of filters, the first position is used to find the document to update
     * @param update_fields array of fields to be updated and their modified values, the first position is used
     * @return boolean information describing success or failure
     *//*

    public boolean UserCUD(Bson[] filters, Bson[] update_fields) {
        return UserCUD(CUDType.UPDATE, CRUDAmount.SINGLE, filters, null, update_fields);
    }

    */
/**
     * Creates User documents in the database
     *
     * @param users array of users to be added into the database
     * @return boolean information describing success or failure
     *//*

    public boolean UserCUD(CRUDAmount amount, User[] users) {
        return UserCUD(CUDType.CREATE, amount, null, users, null);
    }

    */
/**
     * Deletes User documents from the database
     *
     * @param filters array of filters, the first position is used to find the documents to delete
     * @return boolean information describing success or failure
     *//*

    public boolean UserCUD(CRUDAmount amount, Bson[] filters) {
        return UserCUD(CUDType.DELETE, amount, filters, null, null);
    }

    */
/**
     * Updates User documents in the database
     *
     * @param filters       array of filters  used to find the documents to update
     * @param update_fields array of fields to be updated and their modified values
     * @return boolean information describing success or failure
     *//*

    public boolean UserCUD(CRUDAmount amount, Bson[] filters, Bson[] update_fields) {
        return UserCUD(CUDType.UPDATE, amount, filters, null, update_fields);
    }


    */
/**
     * Reads Event objects from the database matching set criteria.
     *
     * @param amount  amount of objects to be found
     * @param filters set of criteria to be matched
     * @return list of Event objects.
     *//*

    public List<Event> EventRead(CRUDAmount amount, Bson filters) {
        MongoCollection<Event> collection = database.getCollection("Event", Event.class);
        if (amount == CRUDAmount.SINGLE) {
            ArrayList<Event> event_list = new ArrayList<>();
            event_list.add(readEvent(filters, collection));
            return event_list;
        } else if (amount == CRUDAmount.MANY) {
            return readEvents(filters, collection);
        } else {
            return null;
        }
    }

    */
/**
     * Reads an Event object from the database matching set criteria.
     *
     * @param filters set of criteria to be matched
     * @return list of Event objects containing a singular match.
     *//*

    public List<Event> EventRead(Bson filters) {
        return EventRead(CRUDAmount.SINGLE, filters);
    }

    */
/**
     * Used to choose the appropriate database connection Event functions based on provided data.
     *
     * @param type          type of CUD operation
     * @param amount        amount of documents to affect with the above
     * @param filters       filters used in DELETE and UPDATE operations
     * @param events        events to be added in the CREATE operation
     * @param update_fields update fields to be modified in the UPDATE operation
     * @return boolean value based on success or failure
     *//*

    private boolean EventCUD(CUDType type, CRUDAmount amount, Bson[] filters, Event[] events, Bson[] update_fields) {
        MongoCollection<Event> collection = database.getCollection("Event", Event.class);
        if (amount == CRUDAmount.SINGLE) {
            if (type == CUDType.CREATE) {
                if (events != null) {
                    return createEvent(events[0], collection);
                }
            } else if (type == CUDType.UPDATE) {
                if (filters != null && update_fields != null) {
                    return updateEvent(update_fields[0], filters[0], collection);
                }
            } else if (type == CUDType.DELETE) {
                if (filters != null) {
                    return deleteEvent(filters[0], collection);
                }
            }
        } else if (amount == CRUDAmount.MANY) {
            if (type == CUDType.CREATE) {
                if (events != null) {
                    return createEvents(events, collection);
                }
            } else if (type == CUDType.UPDATE) {
                if (update_fields != null && filters != null) {
                    return updateEvents(update_fields, filters, collection);
                }
            } else if (type == CUDType.DELETE) {
                if (filters != null) {
                    return deleteEvents(filters[0], collection);
                }
            }
        }
        return false;
    }

    */
/**
     * Creates an Event document in the database
     *
     * @param events array of events, the first position is added to the database
     * @return boolean information describing success or failure
     *//*

    public boolean EventCUD(Event[] events) {
        return EventCUD(CUDType.CREATE, CRUDAmount.SINGLE, null, events, null);
    }

    */
/**
     * Deletes an Event document from the database
     *
     * @param filters array of filters, the first position is used to find the document to delete
     * @return boolean information describing success or failure
     *//*

    public boolean EventCUD(Bson[] filters) {
        return EventCUD(CUDType.DELETE, CRUDAmount.SINGLE, filters, null, null);
    }

    */
/**
     * Updates an Event document in the database
     *
     * @param filters       array of filters, the first position is used to find the document to update
     * @param update_fields array of fields to be updated and their modified values, the first position is used
     * @return boolean information describing success or failure
     *//*

    public boolean EventCUD(Bson[] filters, Bson[] update_fields) {
        return EventCUD(CUDType.UPDATE, CRUDAmount.SINGLE, filters, null, update_fields);
    }

    */
/**
     * Creates Event documents in the database
     *
     * @param events array of events to be added into the database
     * @return boolean information describing success or failure
     *//*

    public boolean EventCUD(CRUDAmount amount, Event[] events) {
        return EventCUD(CUDType.CREATE, amount, null, events, null);
    }

    */
/**
     * Deletes Event documents from the database
     *
     * @param filters array of filters, the first position is used to find the documents to delete
     * @return boolean information describing success or failure
     *//*

    public boolean EventCUD(CRUDAmount amount, Bson[] filters) {
        return EventCUD(CUDType.DELETE, amount, filters, null, null);
    }

    */
/**
     * Updates Event documents in the database
     *
     * @param filters       array of filters  used to find the documents to update
     * @param update_fields array of fields to be updated and their modified values
     * @return boolean information describing success or failure
     *//*

    public boolean EventCUD(CRUDAmount amount, Bson[] filters, Bson[] update_fields) {
        return EventCUD(CUDType.UPDATE, amount, filters, null, update_fields);
    }


    */
/**
     * Reads Category objects from the database matching set criteria.
     *
     * @param amount  amount of objects to be found
     * @param filters set of criteria to be matched
     * @param isSubcategory true/false describing if we are looking for a sub-category
     * @return list of Category objects.
     *//*

    public List<Category> CategoryRead(CRUDAmount amount, Bson filters, boolean isSubcategory) {
        MongoCollection<Category> collection = database.getCollection("Category", Category.class);
        if (amount == CRUDAmount.SINGLE) {
            ArrayList<Category> category_list = new ArrayList<>();
            category_list.add(readCategory(filters, collection));
            return category_list;
        } else if (amount == CRUDAmount.MANY) {
            return readCategories(filters, collection);
        } else {
            return null;
        }
    }

    */
/**
     * Reads a Category object from the database matching set criteria.
     *
     * @param filters set of criteria to be matched
     * @param isSubcategory true/false describing if we are looking for a sub-category
     * @return list of Category objects containing a singular match.
     *//*

    public List<Category> CategoryRead(Bson filters, boolean isSubcategory) {
        return CategoryRead(CRUDAmount.SINGLE, filters, isSubcategory);
    }

    */
/**
     * Reads Category objects from the database matching set criteria.
     *
     * @param amount  amount of objects to be found
     * @param filters set of criteria to be matched
     * @return list of Category objects containing a singular match.
     *//*

    public List<Category> CategoryRead(CRUDAmount amount, Bson filters) {
        return CategoryRead(amount, filters, true);
    }

    */
/**
     * Reads a Category object from the database matching set criteria.
     *
     * @param filters set of criteria to be matched
     * @return list of Category objects containing a singular match.
     *//*

    public List<Category> CategoryRead(Bson filters) {
        return CategoryRead(CRUDAmount.SINGLE, filters, true);
    }



    */
/**
     * Used to choose the appropriate database connection Category functions based on provided data.
     *
     * @param type          type of CUD operation
     * @param amount        amount of documents to affect with the above
     * @param filters       filters used in DELETE and UPDATE operations
     * @param categories    categories to be added in the CREATE operation
     * @param update_fields update fields to be modified in the UPDATE operation
     * @return boolean value based on success or failure
     *//*

    private boolean CategoryCUD(CUDType type, CRUDAmount amount, Bson[] filters, Category[] categories, Bson[] update_fields) {
        MongoCollection<Category> collection = database.getCollection("Category", Category.class);
        if (amount == CRUDAmount.SINGLE) {
            if (type == CUDType.CREATE) {
                if (categories != null) {
                    return createCategory(categories[0], collection);
                }
            } else if (type == CUDType.UPDATE) {
                if (filters != null && update_fields != null) {
                    return updateCategory(update_fields[0], filters[0], collection);
                }
            } else if (type == CUDType.DELETE) {
                if (filters != null) {
                    return deleteCategory(filters[0], collection);
                }
            }
        } else if (amount == CRUDAmount.MANY) {
            if (type == CUDType.CREATE) {
                if (categories != null) {
                    return createCategories(categories, collection);
                }
            } else if (type == CUDType.UPDATE) {
                if (update_fields != null && filters != null) {
                    return updateCategories(update_fields, filters, collection);
                }
            } else if (type == CUDType.DELETE) {
                if (filters != null) {
                    return deleteCategories(filters[0], collection);
                }
            }
        }
        return false;
    }

    */
/**
     * Creates a Category document in the database
     *
     * @param categories array of categories, the first position is added to the database
     * @return boolean information describing success or failure
     *//*

    public boolean CategoryCUD(Category[] categories) {
        return CategoryCUD(CUDType.CREATE, CRUDAmount.SINGLE, null, categories, null);
    }

    */
/**
     * Deletes a Category document from the database
     *
     * @param filters array of filters, the first position is used to find the document to delete
     * @return boolean information describing success or failure
     *//*

    public boolean CategoryCUD(Bson[] filters) {
        return CategoryCUD(CUDType.DELETE, CRUDAmount.SINGLE, filters, null, null);
    }

    */
/**
     * Updates a Category document in the database
     *
     * @param filters       array of filters, the first position is used to find the document to update
     * @param update_fields array of fields to be updated and their modified values, the first position is used
     * @return boolean information describing success or failure
     *//*

    public boolean CategoryCUD(Bson[] filters, Bson[] update_fields) {
        return CategoryCUD(CUDType.UPDATE, CRUDAmount.SINGLE, filters, null, update_fields);
    }

    */
/**
     * Creates Category documents in the database
     *
     * @param categories array of categories to be added into the database
     * @return boolean information describing success or failure
     *//*

    public boolean CategoryCUD(CRUDAmount amount, Category[] categories) {
        return CategoryCUD(CUDType.CREATE, amount, null, categories, null);
    }

    */
/**
     * Deletes Category documents from the database
     *
     * @param filters array of filters, the first position is used to find the documents to delete
     * @return boolean information describing success or failure
     *//*

    public boolean CategoryCUD(CRUDAmount amount, Bson[] filters) {
        return CategoryCUD(CUDType.DELETE, amount, filters, null, null);
    }

    */
/**
     * Updates Category documents in the database
     *
     * @param filters       array of filters  used to find the documents to update
     * @param update_fields array of fields to be updated and their modified values
     * @return boolean information describing success or failure
     *//*

    public boolean CategoryCUD(CRUDAmount amount, Bson[] filters, Bson[] update_fields) {
        return CategoryCUD(CUDType.UPDATE, amount, filters, null, update_fields);
    }


    */
/**
     * Searches the User collection for an exact match.
     *
     * @param filter     criteria of the search
     * @param collection searched mongodb document collection
     * @return User matching the set criteria
     *//*

    private User readUser(Bson filter, MongoCollection<User> collection) {
        return collection.find(filter).first();
    }

    */
/**
     * Searches the Event collection for an exact match.
     *
     * @param filter     criteria of the search
     * @param collection searched mongodb document collection
     * @return Event matching the set criteria
     *//*

    private Event readEvent(Bson filter, MongoCollection<Event> collection) {
        return collection.find(filter).first();
    }

    */
/**
     * Searches the Category collection for an exact match.
     *
     * @param filter     criteria of the search
     * @param collection searched mongodb document collection
     * @return Category matching the set criteria
     *//*

    private Category readCategory(Bson filter, MongoCollection<Category> collection) {
        return collection.find(filter).first();
    }


    */
/**
     * Searches the User collection for matches.
     *
     * @param filter     criteria of the search
     * @param collection searched mongodb document collection
     * @return list of Users matching the set criteria
     *//*

    private ArrayList<User> readUsers(Bson filter, MongoCollection<User> collection) {
        ArrayList<User> user_list = new ArrayList<>();
        try (MongoCursor<User> cursor = collection.find(filter).iterator()) {
            while (cursor.hasNext()) {
                user_list.add(cursor.next());
            }
        }
        return user_list;
    }

    */
/**
     * Searches the Event collection for matches.
     *
     * @param filter     criteria of the search
     * @param collection searched mongodb document collection
     * @return list of Events matching the set criteria
     *//*

    private ArrayList<Event> readEvents(Bson filter, MongoCollection<Event> collection) {
        ArrayList<Event> event_list = new ArrayList<>();
        try (MongoCursor<Event> cursor = collection.find(filter).iterator()) {
            while (cursor.hasNext()) {
                event_list.add(cursor.next());
            }
        }
        return event_list;
    }

    */
/**
     * Searches the Category collection for matches.
     *
     * @param filter     criteria of the search
     * @param collection searched mongodb document collection
     * @return list of Categories matching the set criteria
     *//*

    private ArrayList<Category> readCategories(Bson filter, MongoCollection<Category> collection) {
        // this function needs a way to create sub-categories as well
        // current idea for it: additional conversion for every position in the list of sub-categories
        ArrayList<Category> category_list = new ArrayList<>();
        try (MongoCursor<Category> cursor = collection.find(filter).iterator()) {
            while (cursor.hasNext()) {
                category_list.add(cursor.next());
                // the conversion will happen here
            }
        }
        return category_list;
    }


    */
/**
     * Creates a new document in the User collection.
     *
     * @param user       User to be added
     * @param collection mongodb document collection being added to
     * @return boolean value representing success or failure
     *//*

    private boolean createUser(User user, MongoCollection<User> collection) {
        try {
            InsertOneResult result = collection.insertOne(user);
            System.out.println("Inserted a User with the following id: "
                    + result.getInsertedId().asInt32().getValue());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    */
/**
     * Creates a new document in the Event collection.
     *
     * @param event      Event to be added
     * @param collection mongodb document collection being added to
     * @return boolean value representing success or failure
     *//*

    private boolean createEvent(Event event, MongoCollection<Event> collection) {
        try {
            InsertOneResult result = collection.insertOne(event);
            System.out.println("Inserted an Event with the following id: "
                    + result.getInsertedId().asInt32().getValue());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    */
/**
     * Creates a new document in the Category collection.
     *
     * @param category   Category to be added
     * @param collection mongodb document collection being added to
     * @return boolean value representing success or failure
     *//*

    private boolean createCategory(Category category, MongoCollection<Category> collection) {
        try {
            InsertOneResult result = collection.insertOne(category);
            System.out.println("Inserted a Category with the following id: "
                    + result.getInsertedId().asInt32().getValue());
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    */
/**
     * Creates new documents in the User collection.
     *
     * @param users      array of User objects to be added
     * @param collection mongodb document collection being added to
     * @return boolean value representing success or failure
     *//*

    private boolean createUsers(User[] users, MongoCollection<User> collection) {
        List<Integer> insertedIds = new ArrayList<>();
        try {
            InsertManyResult result = collection.insertMany(Arrays.asList(users));
            result.getInsertedIds().values()
                    .forEach(doc -> insertedIds.add(doc.asInt32().getValue()));
            System.out.println("Inserted Users with the following ids: " + insertedIds);
            return true;
        } catch (MongoBulkWriteException exception) {
            exception.getWriteResult().getInserts()
                    .forEach(doc -> insertedIds.add(doc.getId().asInt32().getValue()));
            System.out.println("A MongoBulkWriteException occurred, but there are " +
                    "successfully processed Users with the following ids: " + insertedIds);
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    */
/**
     * Creates new documents in the Event collection.
     *
     * @param events     array of Event objects to be added
     * @param collection mongodb document collection being added to
     * @return boolean value representing success or failure
     *//*

    private boolean createEvents(Event[] events, MongoCollection<Event> collection) {
        List<Integer> insertedIds = new ArrayList<>();
        try {
            InsertManyResult result = collection.insertMany(Arrays.asList(events));
            result.getInsertedIds().values()
                    .forEach(doc -> insertedIds.add(doc.asInt32().getValue()));
            System.out.println("Inserted Events with the following ids: " + insertedIds);
            return true;
        } catch (MongoBulkWriteException exception) {
            exception.getWriteResult().getInserts()
                    .forEach(doc -> insertedIds.add(doc.getId().asInt32().getValue()));
            System.out.println("A MongoBulkWriteException occurred, but there are " +
                    "successfully processed Events with the following ids: " + insertedIds);
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    */
/**
     * Creates new documents in the Category collection.
     *
     * @param categories array of Category objects to be added
     * @param collection mongodb document collection being added to
     * @return boolean value representing success or failure
     *//*

    private boolean createCategories(Category[] categories, MongoCollection<Category> collection) {
        List<Integer> insertedIds = new ArrayList<>();
        try {
            InsertManyResult result = collection.insertMany(Arrays.asList(categories));
            result.getInsertedIds().values()
                    .forEach(doc -> insertedIds.add(doc.asInt32().getValue()));
            System.out.println("Inserted Categories with the following ids: " + insertedIds);
            return true;
        } catch (MongoBulkWriteException exception) {
            exception.getWriteResult().getInserts()
                    .forEach(doc -> insertedIds.add(doc.getId().asInt32().getValue()));
            System.out.println("A MongoBulkWriteException occurred, but there are " +
                    "successfully processed Categories with the following ids: " + insertedIds);
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    */
/**
     * Update a document in the User collection.
     *
     * @param update_fields fields to be updated and their modified values
     * @param filter        criteria of the document to be updated
     * @param collection    mongodb document collection being updated
     * @return boolean value representing success or failure
     *//*

    private boolean updateUser(Bson update_fields, Bson filter, MongoCollection<User> collection) {
        try {
            collection.updateOne(filter, update_fields);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    */
/**
     * Update a document in the Event collection.
     *
     * @param update_fields fields to be updated and their modified values
     * @param filter        criteria of the document to be updated
     * @param collection    mongodb document collection being updated
     * @return boolean value representing success or failure
     *//*

    private boolean updateEvent(Bson update_fields, Bson filter, MongoCollection<Event> collection) {
        try {
            collection.updateOne(filter, update_fields);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    */
/**
     * Update a document in the Category collection.
     *
     * @param update_fields fields to be updated and their modified values
     * @param filter        criteria of the document to be updated
     * @param collection    mongodb document collection being updated
     * @return boolean value representing success or failure
     *//*

    private boolean updateCategory(Bson update_fields, Bson filter, MongoCollection<Category> collection) {
        try {
            collection.updateOne(filter, update_fields);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    */
/**
     * Update documents in the User collection.
     *
     * @param update_fields array of fields to be updated and their modified values
     * @param filters       array of criteria a document must meet to be updated
     * @param collection    mongodb document collection being updated
     * @return boolean value representing success or failure
     *//*

    private boolean updateUsers(Bson[] update_fields, Bson[] filters, MongoCollection<User> collection) {
        if (update_fields.length == filters.length && update_fields.length > 1) {
            for (int i = 0; i < update_fields.length; i++) {
                try {
                    collection.updateOne(filters[i], update_fields[i]);
                    return true;
                } catch (Exception ignored) {
                }
            }
        } else if (filters.length == 1 && update_fields.length == 1) {
            try {
                UpdateResult result = collection.updateMany(filters[0], update_fields[0]);
                if (result.getMatchedCount() == result.getModifiedCount()) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    */
/**
     * Update documents in the Event collection.
     *
     * @param update_fields array of fields to be updated and their modified values
     * @param filters       array of criteria a document must meet to be updated
     * @param collection    mongodb document collection being updated
     * @return boolean value representing success or failure
     *//*

    private boolean updateEvents(Bson[] update_fields, Bson[] filters, MongoCollection<Event> collection) {
        if (update_fields.length == filters.length && update_fields.length > 1) {
            for (int i = 0; i < update_fields.length; i++) {
                try {
                    collection.updateOne(filters[i], update_fields[i]);
                    return true;
                } catch (Exception ignored) {
                }
            }
        } else if (filters.length == 1 && update_fields.length == 1) {
            try {
                UpdateResult result = collection.updateMany(filters[0], update_fields[0]);
                if (result.getMatchedCount() == result.getModifiedCount()) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    */
/**
     * Update documents in the Category collection.
     *
     * @param update_fields array of fields to be updated and their modified values
     * @param filters       array of criteria a document must meet to be updated
     * @param collection    mongodb document collection being updated
     * @return boolean value representing success or failure
     *//*

    private boolean updateCategories(Bson[] update_fields, Bson[] filters, MongoCollection<Category> collection) {
        if (update_fields.length == filters.length && update_fields.length > 1) {
            for (int i = 0; i < update_fields.length; i++) {
                try {
                    collection.updateOne(filters[i], update_fields[i]);
                    return true;
                } catch (Exception ignored) {
                }
            }
        } else if (filters.length == 1 && update_fields.length == 1) {
            try {
                UpdateResult result = collection.updateMany(filters[0], update_fields[0]);
                if (result.getMatchedCount() == result.getModifiedCount()) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }


    */
/**
     * Delete a document in the User collection.
     *
     * @param filter     criteria of the document to be deleted
     * @param collection mongodb document collection being deleted from
     * @return boolean value representing success or failure
     *//*

    private boolean deleteUser(Bson filter, MongoCollection<User> collection) {
        try {
            collection.deleteOne(filter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    */
/**
     * Delete a document in the Event collection.
     *
     * @param filter     criteria of the document to be deleted
     * @param collection mongodb document collection being deleted from
     * @return boolean value representing success or failure
     *//*

    private boolean deleteEvent(Bson filter, MongoCollection<Event> collection) {
        try {
            collection.deleteOne(filter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    */
/**
     * Delete a document in the Category collection.
     *
     * @param filter     criteria of the document to be deleted
     * @param collection mongodb document collection being deleted from
     * @return boolean value representing success or failure
     *//*

    private boolean deleteCategory(Bson filter, MongoCollection<Category> collection) {
        try {
            collection.deleteOne(filter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    */
/**
     * Delete documents in the User collection.
     *
     * @param filter     criteria of the documents to be deleted
     * @param collection mongodb document collection being deleted from
     * @return boolean value representing success or failure
     *//*

    private boolean deleteUsers(Bson filter, MongoCollection<User> collection) {
        try {
            collection.deleteMany(filter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    */
/**
     * Delete documents in the Event collection.
     *
     * @param filter     criteria of the documents to be deleted
     * @param collection mongodb document collection being deleted from
     * @return boolean value representing success or failure
     *//*

    private boolean deleteEvents(Bson filter, MongoCollection<Event> collection) {
        try {
            collection.deleteMany(filter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    */
/**
     * Delete documents in the Category collection.
     *
     * @param filter     criteria of the documents to be deleted
     * @param collection mongodb document collection being deleted from
     * @return boolean value representing success or failure
     *//*

    private boolean deleteCategories(Bson filter, MongoCollection<Category> collection) {
        try {
            collection.deleteMany(filter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}*/
