package com.lovecloud.auth.presentation.request;

import com.lovecloud.auth.application.command.WeddingSignUpCommand;
import com.lovecloud.usermanagement.domain.WeddingRole;
import jakarta.validation.constraints.*;

public record WeddingSignUpRequest(
        @NotBlank
        @Email(message = "email 형식이여야 합니다.")
        String email,

        @NotBlank
        @Size(min = 2, max = 5, message = "이름은 2자리 이상, 5자리 이하여야 합니다.")
        String name,

        @NotBlank
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 길이, 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.")
        String password,

        @NotBlank
        @Pattern(regexp = "^(010|011)\\d{8}$", message = "전화번호는 010 또는 011로 시작하고 8자리 숫자여야 합니다.")
        String phoneNumber,

        @NotNull
        WeddingRole weddingRole
) {
    public WeddingSignUpCommand toCommand() {
        return new WeddingSignUpCommand(
                email,
                password,
                name,
                phoneNumber,
                weddingRole
        );
    }
}
