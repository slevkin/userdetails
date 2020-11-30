package com.sergey.userdetails.validator;

import com.sergey.userdetails.entity.UserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;
@Component
public class UserValidator implements Validator {

    /**
     * This Validator validates *just* Person instances
     */
    public boolean supports(Class clazz) {
        return UserEntity.class.equals(clazz);
    }

    public void validate(Object obj, Errors e) {
        UserEntity user = (UserEntity) obj;
        if (StringUtils.isNotEmpty(user.getEmpid())){
            Pattern pattern = Pattern.compile("\\d+");
            if(!pattern.matcher(user.getEmpid()).matches()){
                e.rejectValue("empid", "nonnumeric", "EmpId should be numeric");
            }
        } else {
            e.rejectValue("empid", "empty", "EmpId should not be empty");
        }
    }
}