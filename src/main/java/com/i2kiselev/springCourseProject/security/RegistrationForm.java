package com.i2kiselev.springCourseProject.security;

import com.i2kiselev.springCourseProject.model.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Additional class for validating and acquiring User data
 */

@Data
class RegistrationForm {

    @NotBlank(message = "Name is required")
    private String username;

    @Length(min = 5, message = "Password length should be more than 5 characters")
    @NotBlank
    private String password;

    @Pattern(regexp = "\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+", message = "Your name contains forbidden characters")
    private String fullName;

    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$", message = "Wrong format of number")
    private String phone;

    @Pattern(regexp = "[^@]+@[^.]+\\..+", message = "Wrong format of email")
    @NotBlank
    private String email;

    User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password),
                fullName, phone, email);
    }
}
