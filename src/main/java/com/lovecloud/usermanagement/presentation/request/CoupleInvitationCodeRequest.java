package com.lovecloud.usermanagement.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CoupleInvitationCodeRequest(
     @NotBlank
     @Pattern(regexp = "^.{8}$", message = "초대 코드는 정확히 8자리여야 합니다.")
     String invitationCode
) {
}
