package com.order.ecommerce.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@Transactional
@AutoConfigureEmbeddedDatabase
@AutoConfigureMockMvc
@Sql("/product/insert_prerequisite_records.sql")
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetProduct() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/products/1")
                ).andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(
                        content().json("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"sku\": \"1001\",\n" +
                                "  \"title\": \"protein\",\n" +
                                "  \"description\": \"Whey\",\n" +
                                "  \"price\": 1.99\n" +
                                "}")
                ).andReturn();
    }
}
