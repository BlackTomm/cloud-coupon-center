package org.coding.coupon.template.config.domain;

import lombok.AllArgsConstructor;

/**
 * Description:
 * Create by blacktom on 2023/11/27
 */
@AllArgsConstructor
public class MailCrendential {
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
