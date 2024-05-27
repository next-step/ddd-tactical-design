package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;


@Embeddable
public class DisplayedName {
    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private Name name;

    protected DisplayedName() {}

    public DisplayedName(boolean displayed, String name) {
        this(displayed, new Name(name));
    }

    public DisplayedName(boolean displayed, String name, MenuNameValidationService menuNameValidationService) {
        this(displayed, new Name(name, menuNameValidationService));
    }

    public DisplayedName(boolean displayed, Name name) {
        this.displayed = displayed;
        this.name = name;
    }

    public void display() {
        displayed = true;
    }

    public void hide() {
        displayed = false;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
