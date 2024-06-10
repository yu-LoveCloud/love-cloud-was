package com.lovecloud.usermanagement.validator;

import com.lovecloud.auth.exception.NotFoundUserException;
import com.lovecloud.auth.exception.NotFoundWeddingUserException;
import com.lovecloud.usermanagement.domain.User;
import com.lovecloud.usermanagement.domain.WeddingUser;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validatorUserExists(User user){
        if(user == null){
            throw new NotFoundUserException();
        }
    }

    public void validatorWeddingUserExists(WeddingUser weddingUser){
        if(weddingUser == null){
            throw new NotFoundWeddingUserException();
        }
    }
}
