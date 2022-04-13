package kitchenpos.menus.domain.tobe.domain.vo;

import kitchenpos.support.vo.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuDisplayed extends ValueObject<MenuDisplayed> {

    @Column(name = "displayed")
    private boolean displayed;

    public MenuDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    protected MenuDisplayed() {

    }

    public boolean isDisplayed() {
        return this.displayed == true;
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
