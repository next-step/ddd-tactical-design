package kitchenpos.menus.domain.tobe.domain.menu;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Displayed {

    @Column(name = "displayed", nullable = false)
    private boolean value;

    protected Displayed() {
    }

    public Displayed(final boolean value) {
        this.value = value;
    }

    public boolean isDisplayed() {
        return value;
    }

    public boolean isHidden() {
        return !value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Displayed displayed = (Displayed) o;
        return value == displayed.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
