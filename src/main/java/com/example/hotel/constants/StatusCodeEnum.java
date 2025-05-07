package com.example.hotel.constants;

import lombok.Getter;

public enum StatusCodeEnum {
    R_001("Success"),
    R_002("password not valid"),
    R_003("The user was not found"),
    R_004("The room was not found"),
    R_005("Token was generated"),
    R_006("The reservation was not found"),
    R_007("The user already exist"),
    R_008("Invalid refresh token"),
    R_009("The check-in date can not be in the past "),
    R_010("The check-in date must be less than check-out"),
    R_011("The check-in cant not be equal than check-out"),
    R_012("Token refreshed");
    @Getter
    private String description;

    private StatusCodeEnum(String description){
        this.description = description;
    }

}
