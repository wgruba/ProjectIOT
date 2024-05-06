/*
package com.example.springboot.Database;

import com.example.springboot.Event.EventStatus;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

import java.util.List;

*/
/**
 * Functions creating filters needed for database connection.
 *//*

public class DatabaseFilters {

    public DatabaseFilters() {
    }

    */
/**
     * Applicable to any collection.
     *
     * @return filter to get all entries in a collection
     *//*

    public Bson allEntries() {
        return Filters.empty();
    }

    */
/**
     * Applicable to any collection.
     *
     * @param id unique document identifier
     * @return filter to get a specific entry by id
     *//*

    public Bson findById(int id) {
        return equal("_id", id);
    }

    */
/**
     * Applicable to any collection.
     *
     * @param ids list of unique document identifiers
     * @return filter to get a list of specific entries by id
     *//*

    public Bson findAllIds(List<Integer> ids) {
        return oneOf("_id", ids.toArray());
    }

    */
/**
     * Applicable to any collection.
     *
     * @param name document name
     * @return filter to get entries by name, unique for some collections
     *//*

    public Bson findByName(String name) {
        return equal("name", name);
    }

    */
/**
     * Applicable for User collection.
     *
     * @param username name of User
     * @param email    email of User
     * @param password password of User
     * @return filter to verify login
     *//*

    private Bson loginIdentificationFilter(String username, String email, String password) {
        return andFilter(
                orFilter(
                        equal("name", username),
                        equal("mail", email)),
                equal("password", password)
        );
    }

    */
/**
     * Applicable for User collection.
     *
     * @param username_email name or email of User
     * @param password       password of User
     * @return filter to verify login
     *//*

    public Bson loginIdentificationFilter(String username_email, String password) {
        return loginIdentificationFilter(username_email, username_email, password);
    }

    */
/**
     * Applicable for User collection.
     *
     * @param username name of User
     * @param email    email of User
     * @return filter to verify existence of User
     *//*

    private Bson checkUserExistence(String username, String email) {
        return orFilter(
                equal("name", username),
                equal("mail", email)
        );
    }

    */
/**
     * Applicable for User collection.
     *
     * @param username_email name or email of User
     * @return filter to verify existence of User
     *//*

    public Bson checkUserExistence(String username_email) {
        return checkUserExistence(username_email, username_email);
    }

    */
/**
     * Applicable for Event collection.
     *
     * @param id unique organizer (User) identifier
     * @return filter to find Events of a specific organizer
     *//*

    public Bson findThroughOrganizerId(int id) {
        return equal("organizer", id);
    }

    */
/**
     * Applicable for Event collection.
     *
     * @param status current status of the Event
     * @return filter to find Events of a certain status
     *//*

    public Bson findSpecificStatus(EventStatus status) {
        return equal("eventStatus", status);
    }

    */
/**
     * Applicable for Event collection.
     *
     * @param cat_id unique Category identifier
     * @return filter to find Events belonging to a category
     *//*

    public Bson findAllInCategory(int cat_id) {
        return includes("categoryList", cat_id);
    }

    */
/**
     * Applicable to Category collection.
     *
     * @return filter to ensure a category is a parent
     *//*

    public Bson ensureParent() {
        return equal("isParentCategory", true);
    }

    */
/**
     * Applicable to Category collection.
     *
     * @param id unique parent Category identifier
     * @return filter to ensure a sub-category belongs to a parent
     *//*

    public Bson findThroughParentId(int id) {
        return equal("parentId", id);
    }

    public Bson findSubcategory(int id) {
        return equal("subcategories._id", id);
    }

    public Bson findSubcategoryName(String name) {
        return equal("subcategories.name", name);
    }

    */
/**
     * Applicable to filters.
     *
     * @param filters an array of filters to be combined
     * @return a logical 'and' operator of filters
     *//*

    public Bson andFilter(Bson... filters) {
        return Filters.and(filters);
    }

    */
/**
     * Applicable to filters.
     *
     * @param filters an array of filters to be combined
     * @return a logical 'or' operator of filters
     *//*

    public Bson orFilter(Bson... filters) {
        return Filters.or(filters);
    }

    */
/**
     * Applicable to filters.
     *
     * @param filter a filter to be negated
     * @return a logical 'not' operator of a filter
     *//*

    public Bson notFilter(Bson filter) {
        return Filters.not(filter);
    }

    */
/**
     * Filter creation.
     *
     * @param key   name of the field to be compared
     * @param value value to compare the field to
     * @return a filter matching a field with an exact value
     *//*

    private Bson equal(String key, Object value) {
        return Filters.eq(key, value);
    }

    */
/**
     * Filter creation.
     *
     * @param key   name of the field to be compared
     * @param value value to compare the field to
     * @return a filter matching a field with an exact or greater value
     *//*

    private Bson greaterOrEqual(String key, Object value) {
        return Filters.gte(key, value);
    }

    */
/**
     * Filter creation.
     *
     * @param key   name of the field to be compared
     * @param value value to compare the field to
     * @return a filter matching a field with an exact or lesser value
     *//*

    private Bson lesserOrEqual(String key, Object value) {
        return Filters.lte(key, value);
    }

    */
/**
     * Filter creation.
     *
     * @param key    name of the field to be compared
     * @param values values to compare the field to
     * @return a filter matching a field with one of the elements in an array
     *//*

    private Bson oneOf(String key, Object[] values) {
        return Filters.in(key, values);
    }

    */
/**
     * Filter creation.
     *
     * @param key   name of the field to be compared
     * @param value value to search the field for
     * @return a filter matching one of a field's elements with a value
     *//*

    private Bson includes(String key, Object value) {
        return Filters.elemMatch(key, Filters.eq(value));
    }
}*/
