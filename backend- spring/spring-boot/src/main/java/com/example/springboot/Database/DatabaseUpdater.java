/*
package com.example.springboot.Database;

import com.example.springboot.Category.Category;
import com.example.springboot.Event.AgeGroup;
import com.example.springboot.Event.EventStatus;
import com.example.springboot.User.PermissionLevel;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.util.List;

import static com.mongodb.client.model.Updates.currentDate;

*/
/**
 * Functions creating update specifications used in database connection.
 *//*

public class DatabaseUpdater {
    public DatabaseUpdater() {
    }

    */
/**
     * Applicable to any collection.
     *
     * @param new_name New name to set for the document
     * @return update specification for the setting of a new name
     *//*

    public Bson updateName(String new_name) {
        return Updates.set("name", new_name);
    }

    */
/**
     * Applicable to any collection.
     *
     * @return update specification for logging the date of a modification
     *//*

    public Bson updateModificationDate() {
        return currentDate("lastModified");
    }

    */
/**
     * Applicable to any collection.
     *
     * @param updates a list of updates to combine
     * @return update specification for combining multiple smaller updates
     *//*

    public Bson combineUpdates(Bson... updates) {
        return Updates.combine(updates);
    }


    */
/**
     * Applicable to the User collection.
     *
     * @param new_mail New mail to set for the document
     * @return update specification for the setting of a new email address
     *//*

    public Bson updateEmail(String new_mail) {
        return Updates.set("mail", new_mail);
    }

    */
/**
     * Applicable to the User collection.
     *
     * @param new_password New password to set for the document
     * @return update specification for the setting of a new password
     *//*

    public Bson updatePassword(String new_password) {
        return Updates.set("password", new_password);
    }

    */
/**
     * Applicable to the User collection.
     *
     * @param new_lvl New permission level to set for the document
     * @return update specification for the setting of a new permission level
     *//*

    public Bson updatePermissionLevel(PermissionLevel new_lvl) {
        return Updates.set("permissionLevel", new_lvl);
    }

    */
/**
     * Applicable to the User collection.
     *
     * @param cat_ids Categories the User is subscribed to
     * @return update specification for the update of a User';'s Category subscription list
     *//*

    public Bson updateUserCategorySubscriptions(List<Integer> cat_ids) {
        return Updates.set("subscribedCategories", cat_ids);
    }

    */
/**
     * Applicable to the User collection.
     *
     * @param event_ids Events the User is subscribed to
     * @return update specification for the update of a User's Event subscription list
     *//*

    public Bson updateUserEventSubscriptions(List<Integer> event_ids) {
        return Updates.set("subscribedEvents", event_ids);
    }

    */
/**
     * Applicable to the User collection.
     *
     * @param event_id Event to subscribe the User to
     * @return update specification for the addition of a new Event subscription
     *//*

    public Bson subscribeUserToEvent(int event_id) {
        return Updates.addToSet("subscribedEvents", event_id);
    }

    */
/**
     * Applicable to the User collection.
     *
     * @param cat_id Category to subscribe the User to
     * @return update specification for the addition of a new Category subscription
     *//*

    public Bson subscribeUserToCategory(int cat_id) {
        return Updates.addToSet("subscribedCategories", cat_id);
    }

    */
/**
     * Applicable to the User collection.
     *
     * @param event_id Event to unsubscribe the User from
     * @return update specification for the removal of an Event subscription
     *//*

    public Bson unsubscribeUserFromEvent(int event_id) {
        return Updates.pull("subscribedEvents", event_id);
    }

    */
/**
     * Applicable to the User collection.
     *
     * @param cat_id Category to unsubscribe the User from
     * @return update specification for the removal of a Category subscription
     *//*

    public Bson unsubscribeUserFromCategory(int cat_id) {
        return Updates.pull("subscribedCategories", cat_id);
    }


    */
/**
     * Applicable to the Event collection.
     *
     * @param cat_ids List of the Events Categories
     * @return update specification for the update of an Even't Category List
     *//*

    public Bson updateCategoryList(List<Integer> cat_ids) {
        return Updates.set("categoryList", cat_ids);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param cat_id Category to add the Event to
     * @return update specification for the adding of an Event to a Category
     *//*

    public Bson addToCategory(int cat_id) {
        return Updates.addToSet("categoryList", cat_id);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param cat_id Category to remove the Event from
     * @return update specification for the removal of an Event from a Category
     *//*

    public Bson removeFromCategory(int cat_id) {
        return Updates.pull("categoryList", cat_id);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param new_description Description to replace the old one
     * @return update specification for setting a new Event description
     *//*

    public Bson updateDescription(String new_description) {
        return Updates.set("description", new_description);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param new_size Size to replace the old one
     * @return update specification for setting a new Event size
     *//*

    public Bson updateSize(int new_size) {
        return Updates.set("size", new_size);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param new_local Localisation to replace the old one
     * @return update specification for setting a new Event localisation
     *//*

    public Bson updateLocalisation(String new_local) {
        return Updates.set("localisation", new_local);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param is_free true/false describing whether an event is free
     * @return update specification for setting a new paid event status
     *//*

    public Bson updateIsFree(boolean is_free) {
        return Updates.set("isFree", is_free);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param is_free true/false describing whether an event is reservation only
     * @return update specification for setting a new reservation only event status
     *//*

    public Bson updateIsReservationNecessary(boolean is_free) {
        return Updates.set("isReservationNecessary", is_free);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param new_age_group value describing the allowed age of participants
     * @return update specification for setting a new permitted age group of an Event
     *//*

    public Bson updateAgeGroup(AgeGroup new_age_group) {
        return Updates.set("ageGroup", new_age_group);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param new_start_date date of the Event's start
     * @return update specification for setting a new start time of an Event
     *//*

    public Bson updateStartDate(LocalDateTime new_start_date) {
        return Updates.set("startDate", new_start_date);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param new_end_date date of the Event's end
     * @return update specification for setting a new end time of an Event
     *//*

    public Bson updateEndDate(LocalDateTime new_end_date) {
        return Updates.set("endDate", new_end_date);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param new_status new status of the Event
     * @return update specification for setting a new Event status
     *//*

    public Bson updateEventStatus(EventStatus new_status) {
        return Updates.set("eventStatus", new_status);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param user_ids list of subscribed Users
     * @return update specification for updating a list of subscribed Users
     *//*

    public Bson updateUserSubscription(List<Integer> user_ids) {
        return Updates.set("clientList", user_ids);
    }

    */
/**
     * Applicable to the Event collections
     *
     * @param user_id User subscribing
     * @return update specification for the adding of a User subscription
     *//*

    public Bson addUserSubscription(int user_id) {
        return Updates.addToSet("clientList", user_id);
    }

    */
/**
     * Applicable to the Event collection.
     *
     * @param user_id User unsubscribing
     * @return update specification for the removal of a User subscription
     *//*

    public Bson removeUserSubscription(int user_id) {
        return Updates.pull("clientList", user_id);
    }

    */
/**
     * Applicable to the Category collection.
     *
     * @param new_is_parent true/false describing whether a category is a parent
     * @return update specification for setting a new parent category status
     *//*

    public Bson updateIsParent(boolean new_is_parent){
        return Updates.set("isParentCategory", new_is_parent);
    }

    */
/**
     * Applicable to the Category collection.
     *
     * @param subcats List of sub-categorie under the parent
     * @return update specification for setting a new list of sub-categories for a Category
     *//*

    public Bson updateSubcategory(List<Category> subcats) {
        return Updates.set("subcategories", subcats);
    }

    */
/**
     * Applicable to the Category collection.
     *
     * @param name Name of a sub-category
     * @return update specification for setting a new list of sub-categories for a Category
     *//*

    public Bson updateSubcategoryName(String name) {
        return Updates.set("subcategories.name", name);
    }

    */
/**
     * Applicable to the Category collection.
     *
     * @param subcat Sub-category under the parent
     * @return update specification for adding new sub-categories to a Category
     *//*

    public Bson addSubcategory(Category subcat) {
        return Updates.addToSet("subcategories", subcat);
    }

    */
/**
     * Applicable to the Category collection.
     *
     * @param subcat_id Sub-category under the parent
     * @return update specification for removing sub-categories from a Category
     *//*

    public Bson removeSubcategory(int subcat_id) {
        return Updates.pull("subcategories._id", subcat_id);
    }

}
*/
