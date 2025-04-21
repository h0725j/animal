package com.test.animal.entity.enums;

import lombok.Getter;

@Getter
public enum DivisionNm {
    ANIMAL_HOSPITAL("동물병원"),
    INDIVIDUAL("개인"),
    CORPORATION("법인"),
    ORGANIZATION("단체");

    private final String name;

    DivisionNm(String name) {
        this.name = name;
    }
}
