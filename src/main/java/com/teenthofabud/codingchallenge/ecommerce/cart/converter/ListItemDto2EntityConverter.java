package com.teenthofabud.codingchallenge.ecommerce.cart.converter;

import com.teenthofabud.codingchallenge.ecommerce.cart.model.ListItemDto;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ListItemEntity;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.ShoppingListEntity;
import com.teenthofabud.codingchallenge.ecommerce.cart.repository.ShoppingListRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Converts ListItemDto to ListItemEntity.
 * It retrieves the ShoppingListEntity by ID and adds the ListItemEntity to it.
 */
@Component
public class ListItemDto2EntityConverter implements Converter<ListItemDto, ListItemEntity> {

    private final ShoppingListRepository shoppingListRepository;

    public ListItemDto2EntityConverter(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    /**
     * Converts ListItemDto to ListItemEntity.
     * It retrieves the ShoppingListEntity by ID and adds the ListItemEntity to it.
     *
     * @param source ListItemDto containing the item details and cart ID
     * @return ListItemEntity with the item ID and quantity
     */
    @Override
    public ListItemEntity convert(ListItemDto source) {
        ListItemEntity listItemEntity = ListItemEntity.builder().itemId(source.getForm().getItemId()).quantity(source.getForm().getQuantity()).build();
        Optional<ShoppingListEntity> optionalShoppingCartEntity = shoppingListRepository.findById(source.getListId());
        ShoppingListEntity shoppingListEntity = optionalShoppingCartEntity.get();
        shoppingListEntity.addItemToList(listItemEntity);
        return listItemEntity;
    }
}
