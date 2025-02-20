package kitchenpos.menu.tobe.domain.menu;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class MenuDisplayStatus {
    @Column(name = "displayed", nullable = false)
    private boolean value;

    protected MenuDisplayStatus() {}

    public MenuDisplayStatus(boolean value) {
        this.value = value;
    }

    public boolean isDisplayed() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDisplayStatus that = (MenuDisplayStatus) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
