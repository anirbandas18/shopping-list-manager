package com.teenthofabud.codingchallenge.ecommerce;

import com.teenthofabud.codingchallenge.ecommerce.model.event.AddedToListEvent;
import com.teenthofabud.codingchallenge.ecommerce.model.event.DeletedFromListEvent;
import com.teenthofabud.codingchallenge.ecommerce.tracking.model.ItemInteractionEntity;
import com.teenthofabud.codingchallenge.ecommerce.tracking.model.UserInteractionEntity;
import com.teenthofabud.codingchallenge.ecommerce.tracking.repository.ItemInteractionRepository;
import com.teenthofabud.codingchallenge.ecommerce.tracking.repository.UserInteractionRepository;
import com.teenthofabud.codingchallenge.ecommerce.tracking.InteractionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@SpringBootTest
public class TrackingServiceTest {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserInteractionRepository userInteractionRepository;
    private final ItemInteractionRepository itemInteractionRepository;

    @Autowired
    public TrackingServiceTest(ApplicationEventPublisher applicationEventPublisher, UserInteractionRepository userInteractionRepository, ItemInteractionRepository itemInteractionRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.userInteractionRepository = userInteractionRepository;
        this.itemInteractionRepository = itemInteractionRepository;
    }

    @BeforeEach
    void setup() {
        userInteractionRepository.deleteAll();
        itemInteractionRepository.deleteAll();
    }

    @Test
    void testWhenReceivingPublishAddedToCartEventThenRecordEventReceptionOfUserInteractionWithItemOnAddToCartAction() {
        AddedToListEvent event = AddedToListEvent.builder().cartId(1l).itemId(2l).username("user").build();

        applicationEventPublisher.publishEvent(event);

        List<UserInteractionEntity> userInteractionEntities = userInteractionRepository.findAll();
        Assertions.assertEquals(1, userInteractionEntities.size());
        Assertions.assertEquals(event.getCartId(), userInteractionEntities.get(0).getListId());
        Assertions.assertEquals(event.getUsername(), userInteractionEntities.get(0).getUsername());
        Assertions.assertEquals(event.getItemId(), userInteractionEntities.get(0).getItemId());
        Assertions.assertEquals(InteractionType.ADDED_TO_LIST, userInteractionEntities.get(0).getAction());
        List<ItemInteractionEntity> itemInteractionEntities = itemInteractionRepository.findAll();
        Assertions.assertEquals(1, itemInteractionEntities.size());
        Assertions.assertEquals(event.getItemId(), itemInteractionEntities.get(0).getItemId());
        Assertions.assertEquals(InteractionType.ADDED_TO_LIST, itemInteractionEntities.get(0).getAction());
    }

    @Test
    void testWhenReceivingPublishDeletedFromCartEventThenRecordEventReception() {
        DeletedFromListEvent event = DeletedFromListEvent.builder().cartId(2l).itemId(1l).username("adas").build();

        applicationEventPublisher.publishEvent(event);

        List<UserInteractionEntity> userInteractionEntities = userInteractionRepository.findAll();
        Assertions.assertEquals(1, userInteractionEntities.size());
        Assertions.assertEquals(event.getCartId(), userInteractionEntities.get(0).getListId());
        Assertions.assertEquals(event.getUsername(), userInteractionEntities.get(0).getUsername());
        Assertions.assertEquals(event.getItemId(), userInteractionEntities.get(0).getItemId());
        Assertions.assertEquals(InteractionType.DELETED_FROM_LIST, userInteractionEntities.get(0).getAction());
        List<ItemInteractionEntity> itemInteractionEntities = itemInteractionRepository.findAll();
        Assertions.assertEquals(1, itemInteractionEntities.size());
        Assertions.assertEquals(event.getItemId(), itemInteractionEntities.get(0).getItemId());
        Assertions.assertEquals(InteractionType.DELETED_FROM_LIST, itemInteractionEntities.get(0).getAction());
    }

}
