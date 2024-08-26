package com.clean.cleanroom.members.dto;

import lombok.Getter;

@Getter
public class OAuthTokenDto {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String scope;
}