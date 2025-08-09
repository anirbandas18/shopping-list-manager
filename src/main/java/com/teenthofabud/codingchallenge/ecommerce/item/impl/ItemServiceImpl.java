package com.teenthofabud.codingchallenge.ecommerce.item.impl;

import com.teenthofabud.codingchallenge.ecommerce.item.converter.ItemForm2EntityConverter;
import com.teenthofabud.codingchallenge.ecommerce.item.converter.ItemProjection2VoConverter;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemAlreadyExistsException;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemInvalidException;
import com.teenthofabud.codingchallenge.ecommerce.item.exception.ItemNotFoundException;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemProjection;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemVo;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemEntity;
import com.teenthofabud.codingchallenge.ecommerce.item.ItemRepository;
import com.teenthofabud.codingchallenge.ecommerce.item.ItemService;
import com.teenthofabud.codingchallenge.ecommerce.item.converter.ItemEntity2VoConverter;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of the ItemService interface.
 * Provides methods to add, retrieve, and list items.
 */
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemEntity2VoConverter itemEntity2VoConverter;
    private final ItemForm2EntityConverter itemForm2EntityConverter;
    private final ItemProjection2VoConverter itemProjection2VoConverter;

    public ItemServiceImpl(ItemRepository itemRepository, ItemEntity2VoConverter itemEntity2VoConverter, ItemForm2EntityConverter itemForm2EntityConverter, ItemProjection2VoConverter itemProjection2VoConverter) {
        this.itemRepository = itemRepository;
        this.itemEntity2VoConverter = itemEntity2VoConverter;
        this.itemForm2EntityConverter = itemForm2EntityConverter;
        this.itemProjection2VoConverter = itemProjection2VoConverter;
    }

    @Override
    public Long addItem(ItemForm itemForm) throws ItemAlreadyExistsException {
        log.info("Adding item with details: {}", itemForm);
        Optional<ItemEntity> optionalItemEntity = itemRepository.findByName(itemForm.getName());
        if(optionalItemEntity.isPresent()) {
            log.warn("Item with name '{}' already exists", itemForm.getName());
            throw new ItemAlreadyExistsException("name", itemForm.getName());
        }
        ItemEntity itemEntity = itemForm2EntityConverter.convert(itemForm);
        assert itemEntity != null;
        log.debug("Adding item entity: {}", itemEntity);
        ItemEntity savedItemEntity = itemRepository.save(itemEntity);
        log.info("Item added with ID: {}", savedItemEntity.getId());
        return savedItemEntity.getId();
    }

    @Override
    public ItemVo getItemById(Long itemId) throws ItemInvalidException, ItemNotFoundException {
        log.info("Retrieving item with ID: {}", itemId);
        if (itemId == null || itemId <= 0) {
            log.warn("Item ID is invalid");
            throw new ItemInvalidException("ID", itemId);
        }
        ItemEntity itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("ID", itemId));
        log.debug("Found item entity: {}", itemEntity);
        ItemVo itemVo = itemEntity2VoConverter.convert(itemEntity);
        log.info("Retrieved item: {}", itemVo);
        return itemVo;
    }

    @Override
    public List<ItemVo> getItems() {
        log.info("Retrieving all items");
        List<ItemProjection> itemProjections = itemRepository.findBy(ItemProjection.class);
        if (itemProjections.isEmpty()) {
            log.warn("No items found");
            return List.of(); // Return an empty list if no items are found
        }
        List<ItemVo> itemVoList = itemProjections.stream().map(itemProjection2VoConverter::convert).toList();
        log.info("Retrieved {} items", itemVoList.size());
        return itemVoList;
    }
}
