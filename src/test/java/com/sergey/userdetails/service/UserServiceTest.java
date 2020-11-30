package com.sergey.userdetails.service;

import com.sergey.userdetails.entity.UserEntity;
import com.sergey.userdetails.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService service;
    @MockBean
    private UserRepository repository;

    @Test
    void testGetUserById() {
        // Setup our mock
        UserEntity userA = new UserEntity("Mr", "Sergey", "Levkin", "male", "123");
        Mockito.doReturn(Optional.of(userA)).when(repository).findById(123L);
        // Execute the service call
        Optional<UserEntity> user = service.findById(123L);

        // Assert the response
        Assertions.assertTrue(user.isPresent());
        Assertions.assertSame(user.get().getFirstName(), "Sergey");
    }


    @Test
    void testUpdateUser() {
        // Setup our mock
        UserEntity userA = new UserEntity("Mr", "Sergey", "Levkin", "male", "123");
        Mockito.doReturn(userA).when(repository).save(userA);
        // Execute the service call
        UserEntity user = service.update(userA);

        // Assert the response
        Assertions.assertSame(user.getFirstName(), "Sergey");
    }

}
