package com.yellowrq.bo;

/**
 * ClassName:UserBO
 * Package:com.yellowrq.bo
 * Description:
 *  用户业务对象
 * @author:yellowrq
 * @date: 2020/1/16 10:23
 */
public class UserBO {

    private String username;
    private String password;
    private String confirmPassword;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
