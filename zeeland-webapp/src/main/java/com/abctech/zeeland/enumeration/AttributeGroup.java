package com.abctech.zeeland.enumeration;

public enum AttributeGroup {

    COMMON(0, "COMMON"),
    AUTO(1, "AUTO"),
    PROPERTY(2, "PROPERTY"),
    JOB(3, "JOB"),
    BOAT(4, "BOAT"),
    COMPANY(5, "COMPANY"),
    LOST_OR_FOUND(6, "LOST_OR_FOUND");

    private int numberValue = 0;
    private String textValue = null;

    AttributeGroup(int numberValue, String textValue) {
        this.numberValue = numberValue;
        this.textValue = textValue;
    }

    public String toTextValue() {
        return this.textValue;
    }

    public int toNumberValue() {
        return this.numberValue;
    }

    public static AttributeGroup fromNumberValue(int value) {
        for (AttributeGroup i : AttributeGroup.values()) {
            if (i.toNumberValue() == value) {
                return i;
            }
        }
        return null;
    }
}
