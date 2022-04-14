package kitchenpos.menus.domain.tobe;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuDisplayed {
    private boolean displayed;

    protected MenuDisplayed() {
    }

    public MenuDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    protected void show() {
        this.displayed = true;
    }

    protected void hide() {
        this.displayed = false;
    }

    protected boolean isDisplayed() {
        return displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuDisplayed)) {
            return false;
        }
        MenuDisplayed that = (MenuDisplayed) o;
        return displayed == that.displayed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayed);
    }
}
