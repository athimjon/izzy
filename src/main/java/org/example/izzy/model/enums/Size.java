package org.example.izzy.model.enums;

public enum Size {
    // Russian Sizes
    RU_40("40"), RU_42("42"), RU_44("44"), RU_46("46"), RU_48("48"),
    RU_50("50"), RU_52("52"), RU_54("54"), RU_56("56"), RU_58("58"),
    RU_60("60"), RU_62("62"), RU_64("64"), RU_66("66"),

    // European Sizes
    EU_32("32"), EU_34("34"), EU_36("36"), EU_38("38"), EU_40("40"),
    EU_42("42"), EU_44("44"), EU_46("46"), EU_48("48"), EU_50("50"),
    EU_52("52"), EU_54("54"), EU_56("56"), EU_58("58"),

    // International Sizes
    INTER_XS("XS"), INTER_S("S"), INTER_M("M"),
    INTER_L("L"), INTER_XL("XL"), INTER_XXL("XXL"),
    INTER_XXXL("XXXL"), INTER_XXXXL("XXXL"), INTER_XXXXXL("XXXL");

    private final String value;

    Size(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
