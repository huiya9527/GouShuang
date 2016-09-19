package com.goushuang.lyz.error;


public class LoginError {
    private String loginErrorMessage;

    public LoginError(String loginErrorMessage){
        this.loginErrorMessage = loginErrorMessage;
    }

    public String getLoginErrorMessage() {
        return loginErrorMessage;
    }

    public void setLoginErrorMessage(String loginErrorMessage) {
        this.loginErrorMessage = loginErrorMessage;
    }
}
