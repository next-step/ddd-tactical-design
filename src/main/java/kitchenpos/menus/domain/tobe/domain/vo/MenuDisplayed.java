package kitchenpos.menus.domain.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuDisplayed {

    @Column(name = "displayed")
    private boolean displayed;

    public MenuDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    protected MenuDisplayed() {

    }

    public boolean getValue() {
        return displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuDisplayed that = (MenuDisplayed) o;

        return displayed == that.displayed;
    }

    @Override
    public int hashCode() {
        return (displayed ? 1 : 0);
    }
}
