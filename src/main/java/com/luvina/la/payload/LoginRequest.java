package com.luvina.la.payload;

import lombok.Data;

/**
 * LoginRequest chứa thông tin đăng nhập.
 */
@Data
public class LoginRequest {

    private String username;
    private String password;
}
