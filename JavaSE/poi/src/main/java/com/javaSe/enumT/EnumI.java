package com.javaSe.enumT;

public enum EnumI {
    PUBLIC("000"), PRIVATE("111");

    private String value = "";

    EnumI(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
