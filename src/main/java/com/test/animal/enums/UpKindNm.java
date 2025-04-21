package com.test.animal.enums;

public enum UpKindNm {
    DOG("개"),
    CAT("고양이"),
    ETC("기타");

    private final String value;

    UpKindNm(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromValue(String value) {
        for (UpKindNm kind : UpKindNm.values()) {
            if (kind.getValue().equals(value)) {
                return String.valueOf(kind.getValue());
            }
        }
        throw new IllegalArgumentException("종류명 오류: " + value);
    }
}

