package com.lovecloud.usermanagement.presentation;

import com.lovecloud.global.usermanager.SecurityUser;
import com.lovecloud.usermanagement.application.UserQueryService;
import com.lovecloud.usermanagement.query.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserQueryController {

    public final UserQueryService userQueryService;

    @GetMapping("/user/me")
    public ResponseEntity<UserInfoResponse> getCurrentUserInfo(
            @AuthenticationPrincipal SecurityUser securityUser
    ){
        final UserInfoResponse userInfoResponse = userQueryService.getUserInfo(securityUser.user());

        return ResponseEntity.ok(userInfoResponse);
    }
}
