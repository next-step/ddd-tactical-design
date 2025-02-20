package kitchenpos.menu.tobe.domain.menu;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class MenuDisplayStatus {
    @Column(name = "displayed", nullable = false)
    private boolean value;

    public static MenuDisplayStatus of(boolean value) {
        return new MenuDisplayStatus(value);
    }

    private MenuDisplayStatus(boolean value) {
        this.value = value;
    }

    protected MenuDisplayStatus() {}

    public boolean isDisplayed() {
        return value;
    }

    public void show() {
        this.value = true;
    }

    public void hide() {
        this.value = false;
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
