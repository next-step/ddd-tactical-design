package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;


@Embeddable
public class DisplayedName {
    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private CleanName cleanName;

    protected DisplayedName() {}

    public DisplayedName(boolean displayed, String name) {
        this(displayed, new CleanName(name));
    }

    public DisplayedName(boolean displayed, String name, MenuNameValidationService menuNameValidationService) {
        this(displayed, new CleanName(name, menuNameValidationService));
    }

    public DisplayedName(boolean displayed, CleanName cleanName) {
        this.displayed = displayed;
        this.cleanName = cleanName;
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
