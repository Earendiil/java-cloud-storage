package com.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	
    private String input;     // email or username
    private String password;

    //this prevents the password from showing in the logs
    @Override
    public String toString() {
        return "LoginRequest(input=" + input + ", password=****)";
    }
}
