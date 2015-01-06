package com.abctech.zeeland.enumeration;

public enum AttributeType {

    STRING(0, "STRING"),
    URL(1, "URL"),
    INTEGER(2, "INTEGER"),
    PRICE_NOK(3, "PRICE_NOK"),
    EMAIL(4, "EMAIL"),
    PREFORMATTED_STRING(5, "PREFORMATTED_STRING"),
    LENGTH_FEET(6, "LENGTH_FEET"),
    AREA_SIZE(7, "AREA_SIZE"),
    DOUBLE(8, "DOUBLE"),
    PRICE_EUR(9, "PRICE_EUR"),
    PRICE_USD(10, "PRICE_USD");

    private int numberValue = 0;
    private String textValue = null;

    AttributeType(int numberValue, String textValue) {
        this.numberValue = numberValue;
        this.textValue = textValue;
    }

    public String toTextValue() {
        return this.textValue;
    }

    public int toNumberValue() {
        return this.numberValue;
    }

    public static AttributeType fromNumberValue(int value) {
        for (AttributeType i : AttributeType.values()) {
            if (i.toNumberValue() == value) {
                return i;
            }
        }
        return null;
    }
}
