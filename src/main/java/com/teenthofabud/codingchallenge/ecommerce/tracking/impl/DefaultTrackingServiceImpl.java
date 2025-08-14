package com.teenthofabud.codingchallenge.ecommerce.tracking.impl;

import com.teenthofabud.codingchallenge.ecommerce.model.event.DeletedFromListEvent;
import com.teenthofabud.codingchallenge.ecommerce.model.event.InteractionEvent;
import com.teenthofabud.codingchallenge.ecommerce.tracking.*;
import com.teenthofabud.codingchallenge.ecommerce.model.event.AddedToListEvent;
import com.teenthofabud.codingchallenge.ecommerce.tracking.model.ItemInteractionEntity;
import com.teenthofabud.codingchallenge.ecommerce.tracking.model.UserInteractionEntity;
import com.teenthofabud.codingchallenge.ecommerce.tracking.repository.ItemInteractionRepository;
import com.teenthofabud.codingchallenge.ecommerce.tracking.repository.UserInteractionRepository;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Default implementation of the TrackingService interface.
 * This service tracks user interactions related to adding and deleting items from the cart.
 * It listens for specific events and saves the corresponding user interaction details to the repository.
 */
@Component
@Slf4j
public class DefaultTrackingServiceImpl implements TrackingService {

    private final UserInteractionRepository userInteractionRepository;
    private final ItemInteractionRepository itemInteractionRepository;

    public DefaultTrackingServiceImpl(ItemInteractionRepository itemInteractionRepository, UserInteractionRepository userInteractionRepository) {
        this.itemInteractionRepository = itemInteractionRepository;
        this.userInteractionRepository = userInteractionRepository;
    }

    private void trackUserInteraction(InteractionEvent event, InteractionType action) {
        log.debug("Tracking user interaction: {}", event);
        UserInteractionEntity userInteractionEntity = UserInteractionEntity.builder()
                .username(event.getUsername())
                .itemId(event.getItemId())
                .listId(event.getCartId())
                .action(action)
                .build();
        log.debug("Creating UserInteractionEntity: {}", userInteractionEntity);
        userInteractionEntity = userInteractionRepository.save(userInteractionEntity);
        log.info("User interaction saved: {}", userInteractionEntity);
    }

    private void trackItemInteraction(InteractionEvent event, InteractionType action) {
        log.debug("Tracking item interaction: {}", event);
        ItemInteractionEntity itemInteractionEntity = ItemInteractionEntity.builder()
                .itemId(event.getItemId())
                .action(action)
                .counter(1L) // Assuming a counter of 1 for each addition
                .build();
        log.debug("Creating ItemInteractionEntity: {}", itemInteractionEntity);
        itemInteractionEntity = itemInteractionRepository.save(itemInteractionEntity);
        log.info("Item interaction saved: {}", itemInteractionEntity);
        log.debug("User interaction and item interaction tracking completed for event: {}", event);
    }

    @EventListener
    @Observed(name = "tracking.add-to-list", contextualName = "trackings.add", lowCardinalityKeyValues = {"shopping-list.tracking-addition.local", "user", "shopping-list.tracking-addition.global", "item"})
    @Override
    public void trackUserInteraction(@Valid AddedToListEvent event) {
        InteractionType action = InteractionType.ADDED_TO_LIST;
        trackItemInteraction(event, action);
        trackUserInteraction(event, action);
        log.debug("User interaction and item interaction tracking completed for event: {}", event);
    }

    @EventListener
    @Observed(name = "tracking.delete-from-list", contextualName = "trackings.remove", lowCardinalityKeyValues = {"shopping-list.tracking-deletion.local", "user", "shopping-list.tracking-deletion.global", "item"})
    @Override
    public void trackUserInteraction(@Valid DeletedFromListEvent event) {
        InteractionType action = InteractionType.DELETED_FROM_LIST;
        trackItemInteraction(event, action);
        trackUserInteraction(event, action);
        log.debug("User interaction and item interaction tracking completed for event: {}", event);
    }
}
