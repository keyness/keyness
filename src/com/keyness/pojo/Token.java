package com.keyness.pojo;

/**
 * Created by Keyness on 2017/11/14.
 */
public class Token {

    private String accessToken;

    private int expiressIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiressIn() {
        return expiressIn;
    }

    public void setExpiressIn(int expiressIn) {
        this.expiressIn = expiressIn;
    }
}
