package kitchenpos.menus.tobe.domain;

public enum Displayed {
    DISPLAYED("공개"),
    HIDDEN("비공개");

    private final String name;

    Displayed(String name) {
        this.name = name;
    }

}
