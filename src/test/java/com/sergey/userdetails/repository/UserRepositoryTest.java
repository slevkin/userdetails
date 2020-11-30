package com.sergey.userdetails.repository;

import com.sergey.userdetails.entity.AddressEntity;
import com.sergey.userdetails.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql("/data.sql")
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository repository;

    @Test
    void testFindById() {
        Optional<UserEntity> userA = repository.findById(11L);
        Assertions.assertTrue(userA.isPresent());
        UserEntity user = userA.get();
        Assertions.assertEquals("John", user.getFirstName());
        Assertions.assertEquals("Coogee", user.getAddress().getCity());
    }

    @Test
    void testUpdate() {
        UserEntity userA = new UserEntity();
        userA.setFirstName("Tanya");
        userA.setLastName("Peterson");
        userA.setGender("female");
        userA.setEmpid("33");
        userA.setId(21L);
        AddressEntity address = new AddressEntity();
        address.setCity("Mascot");
        address.setPostcode(3890);
        address.setState("QLD");
        address.setStreet("On the road");
        address.setId(2L);
        userA.setAddress(address);
        Optional<UserEntity> oldUser = repository.findById(21L);
        UserEntity updatedUser  = repository.save(userA);
        Optional<UserEntity> newUser = repository.findById(21L);
        Assertions.assertTrue(oldUser.isPresent()&&newUser.isPresent());
        Assertions.assertEquals(userA.getFirstName(), newUser.get().getFirstName());
        Assertions.assertNotEquals(userA.getFirstName(), oldUser.get().getFirstName());
        Assertions.assertEquals(userA.getAddress().getState(), newUser.get().getAddress().getState());
        //Check if address.id remains the same
        Assertions.assertEquals(userA.getAddress().getId(), newUser.get().getAddress().getId());
        Assertions.assertEquals(userA.getAddress().getId(), oldUser.get().getAddress().getId());
        //Check if address.state is changed
        Assertions.assertNotEquals(newUser.get().getAddress().getState(), oldUser.get().getAddress().getState());

    }

}
