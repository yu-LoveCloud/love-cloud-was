package com.lovecloud.usermanagement.application;

import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.User;
import com.lovecloud.usermanagement.domain.WeddingUser;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import com.lovecloud.usermanagement.query.response.UserInfoResponse;
import com.lovecloud.usermanagement.query.response.UserInfoResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final CoupleRepository coupleRepository;

    public UserInfoResponse getUserInfo(User user){

        Couple couple = null;
        if(user instanceof WeddingUser){
            WeddingUser weddingUser = (WeddingUser) user;
            couple = coupleRepository.findByMemberIdOrThrow(weddingUser.getId());

        }

        return UserInfoResponseMapper.mapUserToUserInfoResponse(user, couple);
    }
}
