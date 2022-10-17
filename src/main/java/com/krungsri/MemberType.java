package com.krungsri;

public enum MemberType {
    PLATINUM ("Platinum"),
    GOLD("Gold"),
    SILVER("Silver");

    public final String value;

    MemberType(String value) {
        this.value = value;
    }
}
