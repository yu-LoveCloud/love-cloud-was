package com.lovecloud.usermanagement.query.response;

import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.User;


public class UserInfoResponseMapper {

    public static UserInfoResponse mapUserToUserInfoResponse(User user, Couple couple) {
        return new UserInfoResponse(
                user.getId(),
                user.getName(),
                couple != null ? couple.getId() : null
        );
    }
}
