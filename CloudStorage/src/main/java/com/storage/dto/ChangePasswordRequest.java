package com.storage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Old password is required")
    private String oldPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, max = 72)
    @Pattern(regexp = ".*[a-zA-Z].*", message = "New password must contain at least one letter")
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

}
