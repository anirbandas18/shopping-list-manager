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

import java.nio.charset.Charset;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "adas", userDetailsServiceBeanName = "defaultUserServiceImpl", setupBefore = TestExecutionEvent.TEST_METHOD)
public class ShoppingListControllerIT {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ShoppingListControllerIT(MockMvc mockMvc, UserRepository userRepository, ItemRepository itemRepository, ShoppingListRepository shoppingListRepository, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.shoppingListRepository = shoppingListRepository;
        this.objectMapper = objectMapper;
    }

    private ListItemEntity listItemEntity;
    private ShoppingListEntity shoppingListEntity1;
    private ShoppingListEntity shoppingListEntity2;

    private static final String SHOPPING_CART_API_URI = "/api/shopping-list";

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
        listItemEntity = ListItemEntity.builder()
                .itemId(itemEntity.getId())
                .quantity(2)
                .build();
        shoppingListEntity1 = ShoppingListEntity.builder()
                .username(userEntityList.get(0).getUsername())
                .build();
        shoppingListEntity1.addItemToList(listItemEntity);
        shoppingListRepository.save(shoppingListEntity1);
        // Set 2
        itemEntity = ItemEntity.builder()
                .price(1.19)
                .category(Category.DAIRY_ALTERNATIVES)
                .description("Another item description")
                .name("Previous Item")
                .build();
        itemRepository.save(itemEntity);
        listItemEntity = ListItemEntity.builder()
                .itemId(itemEntity.getId())
                .quantity(5)
                .build();
        shoppingListEntity2 = ShoppingListEntity.builder()
                .username(userEntityList.get(1).getUsername())
                .build();
        shoppingListEntity2.addItemToList(listItemEntity);
        shoppingListRepository.save(shoppingListEntity2);

    }

    @Test
    void testPostWithExistingUserOnSuccessShouldReturnCreatedAndLocationResponseHeader() throws Exception {
        mockMvc.perform(post(SHOPPING_CART_API_URI))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(Matchers.blankString()))
                .andExpect(header().exists(HttpHeaders.LOCATION));
    }

    @Test
    void testPostWithAbsentUserOnFailureShouldReturnBadRequestAndInvalidData() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(SHOPPING_CART_API_URI).with(user("qa")))
                .andDo(print()).andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("username"));
    }

    @Test
    void testGetWithExistingIdOnSuccessShouldReturnOkAndDetails() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(SHOPPING_CART_API_URI + "/{id}", shoppingListEntity1.getId())
                        .characterEncoding(Charset.defaultCharset())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(0, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ShoppingListVo.class).id().compareTo(shoppingListEntity1.getId()));
        Assertions.assertEquals(0, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ShoppingListVo.class).userName().compareTo(shoppingListEntity1.getUsername()));
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ShoppingListVo.class).items().size() == shoppingListEntity1.getListItems().size());
    }

    @Test
    void testGetWithNonExistingIdOnFailureShouldReturnNotFound() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(SHOPPING_CART_API_URI + "/{id}", Integer.MAX_VALUE))
                .andDo(print()).andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("id"));
    }

    @Test
    void testGetWithMismatchUserAndCartOnFailureShouldReturnPreconditionFailed() throws Exception {
        List<UserEntity> userEntityList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));

        MvcResult mvcResult = mockMvc.perform(get(SHOPPING_CART_API_URI + "/{id}", shoppingListEntity1.getId())
                        .with(user(userEntityList.get(1).getUsername())))
                .andDo(print()).andExpect(status().isPreconditionFailed())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.PRECONDITION_FAILED.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("username"));
    }

    @Test
    void testGetAllForUserOnSuccessShouldReturnOkAndDetails() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(SHOPPING_CART_API_URI))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(1, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ShoppingListVo[].class).length);
    }

    @Test
    void testGetAllForUserWithNoContentOnSuccessShouldReturnOkAndNoDetails() throws Exception {
        List<UserEntity> userEntityList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));

        mockMvc.perform(get(SHOPPING_CART_API_URI).with(user(userEntityList.get(2).getUsername())))
                .andDo(print()).andExpect(status().isNoContent())
                .andExpect(content().string(Matchers.blankString()))
                .andReturn();

    }

}
