package com.youyk.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값은 제외하고 json으로 변환
@Builder
public record RequestUser(
        @NotNull(message = "Email cannot be null")
        @Size(min = 2, message = "Email must not be less than two characters")
        @Email
        String email,
        @NotNull(message = "Name cannot be null")
        @Size(min = 2, message = "Name must not be less than two characters")
        String name,
        @NotNull(message = "Password cannot be null")
        @Size(min = 8, message = "Password must not be equal or greater than 8 characters")
        String pwd
) {
}
