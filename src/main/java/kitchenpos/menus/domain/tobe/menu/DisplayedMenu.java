package kitchenpos.menus.domain.tobe.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedMenu {

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    protected DisplayedMenu() {
    }

    public DisplayedMenu(boolean displayed) {
        this.displayed = displayed;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DisplayedMenu that = (DisplayedMenu) o;
        return displayed == that.displayed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayed);
    }
}
