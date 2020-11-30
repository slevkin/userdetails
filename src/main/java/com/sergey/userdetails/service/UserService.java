package com.sergey.userdetails.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sergey.userdetails.entity.UserEntity;
import com.sergey.userdetails.repository.UserRepository;
import com.sergey.userdetails.resource.UserResource;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserResource.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @HystrixCommand(fallbackMethod = "fallBackOnFindUser")
    public Optional<UserEntity> findById(Long id){
       return userRepository.findById(id);
    }

    /*This method is used to handle failure of the findUser
    In some scenarios we would get data from cache or return default values.
    For the scope of this task I just return an empty optional.
    */
    public Optional<UserEntity> fallBackOnFindUser(Long id){
        return Optional.empty();
    }

    @Transactional(rollbackFor = IllegalArgumentException.class)
    public UserEntity updateWithDeclarativeTransaction(UserEntity user){
        UserEntity newUser = userRepository.save(user);
        //there is no real need to put validation step after the object persistence,
        // it is just to show the rollback functionality
        if(StringUtils.isEmpty(user.getFirstName())){
            throw new IllegalArgumentException("FirstName is required attribute.");
        }
        return user;
    }

    @HystrixCommand(fallbackMethod = "fallBackOnUpdate")
    public UserEntity update(UserEntity user){
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        UserEntity newUser = userRepository.save(user);
        //there is no real need to put validation step after the object persistence,
        // it is just to show the rollback functionality
        if(StringUtils.isEmpty(user.getFirstName())){
            platformTransactionManager.rollback(transactionStatus);
        }
        platformTransactionManager.commit(transactionStatus);
        return user;
    }


    public UserEntity fallBackOnUpdate(UserEntity user){
        logger.error("fallBackOnUpdate");
        return null;
    }
}
