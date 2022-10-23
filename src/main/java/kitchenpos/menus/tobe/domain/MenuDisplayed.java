package kitchenpos.menus.tobe.domain;

import javax.persistence.Embeddable;

@Embeddable
public class MenuDisplayed {

    private boolean displayed;

    protected MenuDisplayed() {
    }

    public MenuDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public boolean isDisplayed() {
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

    public void hide() {
        this.displayed = false;
    }

    public void display() {
        this.displayed = true;
    }
}
