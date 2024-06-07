package kitchenpos.menus.tobe.domain;

import java.util.Objects;

public enum Displayed {
    DISPLAYED("공개"),
    HIDDEN("비공개");

    private final String name;

    Displayed(String name) {
        this.name = name;
    }

    public static Displayed isDisplayed(Boolean display) {
        Objects.requireNonNull(display, "메뉴의 공개 여부 파라미터는 필수입니다.");
        return display ? DISPLAYED : HIDDEN;
    }

}
