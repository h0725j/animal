package com.test.animal.entity.enums;

import lombok.Getter;

@Getter
public enum ProcessState {
    PROTECTED("보호중"),
    RETURN("종료(반환)"),
    ENTHANASIA("종료(안락사)"),
    NATURAL_DEATH("종료(자연사)"),
    ADOPTION("종료(입양)");

    private final String state;

    ProcessState(String state) {
        this.state = state;
    }
}
