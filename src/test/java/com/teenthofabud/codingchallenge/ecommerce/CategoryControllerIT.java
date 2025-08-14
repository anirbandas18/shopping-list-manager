package com.teenthofabud.codingchallenge.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.codingchallenge.ecommerce.category.Category;
import com.teenthofabud.codingchallenge.ecommerce.category.CategoryVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "adas", userDetailsServiceBeanName = "defaultUserServiceImpl", setupBefore = TestExecutionEvent.TEST_METHOD)
class CategoryControllerIT {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private static final String CATEGORY_API_URI = "/api/category";

    @Autowired
    public CategoryControllerIT(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void testGetAllWithAvailabilityOnSuccessShouldReturnOkAndDetails() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(CATEGORY_API_URI)
                        .characterEncoding(Charset.defaultCharset())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        Assertions.assertNotNull(mvcResult);
        Assertions.assertEquals(Category.values().length, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CategoryVo[].class).length);
    }

}