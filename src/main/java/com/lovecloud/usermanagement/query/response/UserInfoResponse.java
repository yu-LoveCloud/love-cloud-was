package com.lovecloud.usermanagement.query.response;

public record UserInfoResponse(
        Long userId,
        String name,
        Long coupleId
) {

}
