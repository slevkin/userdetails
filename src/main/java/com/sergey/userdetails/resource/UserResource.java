package com.sergey.userdetails.resource;

import com.sergey.userdetails.entity.UserEntity;
import com.sergey.userdetails.service.UserService;
import com.sergey.userdetails.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class UserResource {
    private UserService userService;
    @Autowired
    UserValidator userValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(userValidator);
    }

    private static final Logger logger = LogManager.getLogger(UserResource.class);

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(user -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .eTag(Long.toString(user.getId()))
                                .location(new URI("/api/user/" + user.getId()))
                                .body(user);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user,
                                        @PathVariable Long id) {
        logger.info("Updating user with id: {}, FirstName: {}, LastName: {}",
                id, user.getFirstName(), user.getLastName());
        try {
            UserEntity updated = userService.update(user);
            return ResponseEntity.ok()
                    .location(new URI("/user/" + user.getId()))
                    .body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
