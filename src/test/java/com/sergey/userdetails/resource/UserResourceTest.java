package com.sergey.userdetails.resource;

import com.sergey.userdetails.entity.UserEntity;
import com.sergey.userdetails.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceTest {

    @MockBean
    private UserService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetUserById() throws Exception {
        // Setup our mocked service
        UserEntity userA = new UserEntity("Mr", "Sergey", "Levkin", "male", "123");
        Mockito.doReturn(Optional.of(userA)).when(service).findById(123L);

        // Execute the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{userId}", 123L))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the headers
                .andExpect(jsonPath("$.firstName", is("Sergey")))
                .andExpect(jsonPath("$.lastName", is("Levkin")))
                .andExpect(jsonPath("$.empid", is("123")))
                .andExpect(jsonPath("$.gender", is("male")));
    }


    @Test
    void testGetUserByIdEmpty() throws Exception {
        // Setup our mocked service
        Mockito.doReturn(Optional.empty()).when(service).findById(123L);

        // Execute the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{userId}", 123L))
                // Validate the response code and content type
                .andExpect(status().isNotFound());
    }

}