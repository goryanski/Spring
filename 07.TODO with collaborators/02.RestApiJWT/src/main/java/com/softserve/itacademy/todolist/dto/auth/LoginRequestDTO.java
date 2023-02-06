package com.softserve.itacademy.todolist.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequestDTO {
    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    private String username;

    @Pattern(regexp = "[A-Za-z\\d]{6,}",
            message = "Must be minimum 6 symbols long, using digits and latin letters")
    @Pattern(regexp = ".*\\d.*",
            message = "Must contain at least one digit")
    @Pattern(regexp = ".*[A-Z].*",
            message = "Must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*",
            message = "Must contain at least one lowercase letter")
    private String password;
}
