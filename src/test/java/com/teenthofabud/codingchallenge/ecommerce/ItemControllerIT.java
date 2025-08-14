package com.teenthofabud.codingchallenge.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.codingchallenge.ecommerce.category.Category;
import com.teenthofabud.codingchallenge.ecommerce.item.ItemRepository;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemVo;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemEntity;
import com.teenthofabud.codingchallenge.ecommerce.item.model.ItemForm;
import com.teenthofabud.codingchallenge.ecommerce.model.ErrorVo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "user", userDetailsServiceBeanName = "defaultUserServiceImpl", setupBefore = TestExecutionEvent.TEST_METHOD)
class ItemControllerIT {

    private final MockMvc mockMvc;
    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;

    private ItemForm itemForm;
    private ItemEntity itemEntity;

    private static final String ITEM_API_URI = "/api/item";
    private static final String ERROR_CODE = "INVALID_VALUE";

    @Autowired
    public ItemControllerIT(ItemRepository itemRepository, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.itemRepository = itemRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    void setup() {
        itemRepository.deleteAll();
        itemEntity = ItemEntity.builder()
                .price(100.0)
                .category(Category.DAIRY_ALTERNATIVES)
                .description("Sample item description")
                .name("ExistingItem")
                .build();
        itemRepository.save(itemEntity);
        itemForm = ItemForm.builder()
                .name("NewItem")
                .price(99.99)
                .category(Category.CONFECTIONERY.name())
                .description("New item description")
                .build();
    }

    @WithUserDetails(value = "adas", userDetailsServiceBeanName = "defaultUserServiceImpl", setupBefore = TestExecutionEvent.TEST_METHOD)
    @Test
    void testPostWithValidRequestBodyOnSuccessShouldReturnCreatedAndLocationResponseHeader() throws Exception {
        mockMvc.perform(post(ITEM_API_URI)/*.with(user("adas"))*/
                        .content(objectMapper.writeValueAsString(itemForm))
                        .characterEncoding(Charset.defaultCharset())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(Matchers.blankString()))
                .andExpect(header().exists(HttpHeaders.LOCATION));
    }

    @Test
    void testPostWithValidRequestBodyButNonPermittedUserOnFailureShouldReturnUnauthorized() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(ITEM_API_URI)
                        .content(objectMapper.writeValueAsString(itemForm))
                        .characterEncoding(Charset.defaultCharset())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isForbidden())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.FORBIDDEN.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("Access Denied"));
    }

    @Test
    void testPostWithInvalidCategoryRequestBodyOnFailureShouldReturnBadRequestAndInvalidValue() throws Exception {
        itemForm.setCategory("S");

        MvcResult mvcResult = mockMvc.perform(post(ITEM_API_URI).with(user("adas"))
                        .content(objectMapper.writeValueAsString(itemForm))
                        .characterEncoding(Charset.defaultCharset())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(ERROR_CODE, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("category"));
    }

    @Test
    void testPostWithInvalidPriceRequestBodyOnFailureShouldReturnBadRequestAndInvalidValue() throws Exception {
        itemForm.setPrice(-1d);

        MvcResult mvcResult = mockMvc.perform(post(ITEM_API_URI).with(user("adas"))
                        .content(objectMapper.writeValueAsString(itemForm))
                        .characterEncoding(Charset.defaultCharset())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(ERROR_CODE, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("price"));
    }

    @Test
    void testPostWithInvalidNameRequestBodyOnFailureShouldReturnBadRequestAndInvalidValue() throws Exception {
        itemForm.setName("");

        MvcResult mvcResult = mockMvc.perform(post(ITEM_API_URI).with(user("adas"))
                        .content(objectMapper.writeValueAsString(itemForm))
                        .characterEncoding(Charset.defaultCharset())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(ERROR_CODE, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("name"));
    }

    @WithUserDetails(value = "adas", userDetailsServiceBeanName = "defaultUserServiceImpl", setupBefore = TestExecutionEvent.TEST_METHOD)
    @Test
    void testPostWithDuplicateDataOnFailureShouldReturnConflict() throws Exception {
        itemForm.setName(itemEntity.getName());

        MvcResult mvcResult = mockMvc.perform(post(ITEM_API_URI)/*.with(user("adas"))*/
                        .content(objectMapper.writeValueAsString(itemForm))
                        .characterEncoding(Charset.defaultCharset())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isConflict())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.CONFLICT.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("name"));
    }

    @Test
    void testGetWithExistingIdOnSuccessShouldReturnOkAndDetails() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ITEM_API_URI + "/{id}", itemEntity.getId()))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(0, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ItemVo.class).category().compareTo(itemEntity.getCategory().name()));
        Assertions.assertEquals(0, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ItemVo.class).name().compareTo(itemEntity.getName()));
        Assertions.assertEquals(0, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ItemVo.class).price().compareTo(itemEntity.getPrice()));
        Assertions.assertEquals(0, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ItemVo.class).description().compareTo(itemEntity.getDescription()));
    }

    @Test
    void testGetWithNonExistingIdOnFailureShouldReturnNotFound() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ITEM_API_URI + "/{id}", Integer.MAX_VALUE))
                .andDo(print()).andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("ID"));
    }

    @Test
    void testGetWithNonExistingIdOnFailureShouldReturnBadRequestAndInvalidValue() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ITEM_API_URI + "/{id}", -1))
                .andDo(print()).andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.name(), objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getCode());
        Assertions.assertTrue(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorVo.class).getMessage().contains("ID"));
    }

    @Test
    void testGetAllWithAvailabilityOnSuccessShouldReturnOkAndDetails() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ITEM_API_URI))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(1, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ItemVo[].class).length);
    }

    @Test
    void testGetAllWithUnavailabilityOnSuccessShouldReturnOkAndDetails() throws Exception {
        itemRepository.deleteAll();

        mockMvc.perform(get(ITEM_API_URI))
                .andDo(print()).andExpect(status().isNoContent())
                .andExpect(content().string(Matchers.blankString()))
                .andReturn();

    }
}