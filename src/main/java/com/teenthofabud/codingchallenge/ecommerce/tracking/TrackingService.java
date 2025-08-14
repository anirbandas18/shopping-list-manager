package com.teenthofabud.codingchallenge.ecommerce.tracking;

import com.teenthofabud.codingchallenge.ecommerce.model.event.AddedToListEvent;
import com.teenthofabud.codingchallenge.ecommerce.model.event.DeletedFromListEvent;
import org.springframework.stereotype.Service;

/**
 * Interface for tracking user interactions related to shopping list operations.
 * This service provides methods to track events when users add or delete items from their lists.
 */
@Service
public interface TrackingService {

    /**
     * Tracks user interactions related to adding items to the list.
     *
     * @param event the event containing details about the user interaction
     */
    public void trackUserInteraction(AddedToListEvent event);

    /**
     * Tracks user interactions related to deleting items from the list.
     *
     * @param event the event containing details about the user interaction
     */
    public void trackUserInteraction(DeletedFromListEvent event);

}
