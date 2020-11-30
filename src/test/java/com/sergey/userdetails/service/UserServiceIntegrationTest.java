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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@Sql("/data.sql")
@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired
    private UserService service;

    @Test
    void testCheckRollBack() {
        Optional<UserEntity> userOp = service.findById(21L);
        UserEntity user = userOp.orElseThrow();
        user.setFirstName("");
        try {
            UserEntity updatedUserEntity = service.update(user);
        }catch (Exception e){

        }
        userOp = service.findById(21L);
        Assertions.assertNotEquals("",userOp.orElseThrow().getFirstName());
    }
}
