package com.lovecloud.usermanagement.application;

import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.User;
import com.lovecloud.usermanagement.domain.WeddingUser;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import com.lovecloud.usermanagement.query.response.UserInfoResponse;
import com.lovecloud.usermanagement.query.response.UserInfoResponseMapper;
import com.lovecloud.usermanagement.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final CoupleRepository coupleRepository;
    private final UserValidator userValidator;

    public UserInfoResponse getUserInfo(User user){
        userValidator.validatorUserExists(user);

        Couple couple = null;
        if(user instanceof WeddingUser){
            WeddingUser weddingUser = (WeddingUser) user;
            userValidator.validatorWeddingUserExists(weddingUser);
            couple = coupleRepository.findByMemberId(weddingUser.getId()).orElse(null);

        }

        return UserInfoResponseMapper.mapUserToUserInfoResponse(user, couple);
    }
}
