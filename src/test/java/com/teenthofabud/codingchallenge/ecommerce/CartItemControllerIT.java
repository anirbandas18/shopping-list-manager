package com.teenthofabud.codingchallenge.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.codingchallenge.ecommerce.cart.model.*;
import com.teenthofabud.codingchallenge.ecommerce.cart.repository.ShoppingListRepository;
import com.teenthofabud.codingchallenge.ecommerce.category.Category;
import com.teenthofabud.codingchallenge.ecommerce.item.ItemRepository;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemEntity;
import com.teenthofabud.codingchallenge.ecommerce.model.ErrorVo;
import com.teenthofabud.codingchallenge.ecommerce.user.UserRepository;
import com.teenthofabud.codingchallenge.ecommerce.user.model.UserEntity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "adas", userDetailsServiceBeanName = "defaultUserServiceImpl", setupBefore = TestExecutionEvent.TEST_METHOD)
public class CartItemControllerIT {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CartItemControllerIT(MockMvc mockMvc, UserRepository userRepository, ItemRepository itemRepository, ShoppingListRepository shoppingListRepository, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.shoppingListRepository = shoppingListRepository;
        this.objectMapper = objectMapper;
    }

    private ListItemForm listItemForm;
    private ListItemEntity listItemEntity1;
    private ListItemEntity listItemEntity2;
    private ShoppingListEntity shoppingListEntity1;
    private ShoppingListEntity shoppingListEntity2;

    private static final String CART_ITEM_API_URI = "/api/list-item/shopping-list/{list-id}";

    @BeforeEach
    void setup() {
        // Set 1
        itemRepository.deleteAll();
        shoppingListRepository.deleteAll();
        List<UserEntity> userEntityList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
        ItemEntity itemEntity = ItemEntity.builder()
                .price(100.0)
                .category(Category.DAIRY_ALTERNATIVES)
                .description("Sample item description")
                .name("ExistingItem")
                .build();
        itemRepository.save(itemEntity);
        listItemEntity1 = ListItemEntity.builder()
                .itemId(itemEntity.getId())
                .quantity(2)
                .build();
        shoppingListEntity1 = ShoppingListEntity.builder()
                .username(userEntityList.get(0).getUsername())
                .build();
        shoppingListEntity1.addItemToList(listItemEntity1);
        shoppingListRepository.save(shoppingListEntity1);
        // Set 2
        itemEntity = ItemEntity.builder()
                .price(1.19)
                .category(Category.DAIRY_ALTERNATIVES)
                .description("Another item description")
                .name("Previous Item")
                .build();
        itemRepository.save(itemEntity);
        listItemEntity2 = ListItemEntity.builder()
                .itemId(itemEntity.getId())
                .quantity(5)
                .build();
        shoppingListEntity2 = ShoppingListEntity.builder()
                .username(userEntityList.get(1).getUsername())
                .build();
        shoppingListEntity2.addItemToList(listItemEntity2);
        shoppingListRepository.save(shoppingListEntity2);


        listItemForm = ListItemForm.builder()
                .itemId(listItemEntity2.getItemId())
                .quantity(listItemEntity2.getQuantity())
                .build();
    }

    @Test
    void testPostValidItemInExistingUserCartOnSuccessShouldReturnCreatedAndLocationResponseHeader() throws Exception {
        mockMvc.perform(post(CART_ITEM_API_URI, shoppingListEntity1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(listItemForm)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(Matchers.blankString()))
                .andExpect(header().exists(HttpHeaders.LOCATION));
    }

    @Test
    void testPostInvalidItemIdInExistingUserCartOnFailureShouldReturnBadRequest() throws Exception {
        listItemForm.setItemId(Long.MAX_VALUE);

        MvcResult mvcResult = mockMvc.perform(post(CART_ITEM_API_URI, shoppingListEntity1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(listItemForm)))
                .andDo(print()).andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("itemId"));
    }

    @Test
    void testPostInvalidItemQuantityInExistingUserCartOnFailureShouldReturnBadRequest() throws Exception {
        listItemForm.setQuantity(0);

        MvcResult mvcResult = mockMvc.perform(post(CART_ITEM_API_URI, shoppingListEntity1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(listItemForm)))
                .andDo(print()).andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals("INVALID_VALUE", objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("Quantity"));
    }

    @Test
    void testPostWithAbsentCartOfUserOnFailureShouldReturnNotFound() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(CART_ITEM_API_URI, Integer.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(listItemForm)))
                .andDo(print()).andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("id"));
    }

    @Test
    void testPostWithCartOfDifferentUserOnFailureShouldReturnPreconditionFailed() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(CART_ITEM_API_URI, shoppingListEntity2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(listItemForm)))
                .andDo(print()).andExpect(status().isPreconditionFailed())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.PRECONDITION_FAILED.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("username"));
    }

    @Test
    void testDeleteValidItemInExistingUserCartOnSuccessShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete(CART_ITEM_API_URI, shoppingListEntity1.getId())
                        .param("id", listItemEntity1.getId().toString()))
                .andDo(print()).andExpect(status().isNoContent())
                .andExpect(content().string(Matchers.blankString()));
    }

    @Test
    void testDeleteAbsentItemInExistingUserCartOnFailureShouldReturnNotFound() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(CART_ITEM_API_URI , shoppingListEntity1.getId())
                        .param("id", String.valueOf(Integer.MAX_VALUE)))
                .andDo(print()).andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("id"));
    }

    @Test
    void testDeleteValidItemInAbsentUserCartOnFailureShouldReturnNotFound() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(CART_ITEM_API_URI, Integer.MAX_VALUE)
                        .param("id", listItemEntity1.getId().toString()))
                .andDo(print()).andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("id")); // Domain specific error message
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getDomain().contains("Cart"));
    }

    @Test
    void testDeleteValidItemInDifferentUserCartOnFailureShouldReturnPreConditionFailed() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(CART_ITEM_API_URI, shoppingListEntity2.getId()) // belongs to another user
                        .param("id", listItemEntity2.getId().toString())) // belongs to another user
                .andDo(print()).andExpect(status().isPreconditionFailed())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.PRECONDITION_FAILED.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("username"));
    }

}
