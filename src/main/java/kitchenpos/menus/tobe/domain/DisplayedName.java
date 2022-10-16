package kitchenpos.menus.tobe.domain;

import java.util.Objects;

public class DisplayedName {
    private final String name;

    public DisplayedName(String name, Profanities profanities) {
        if (profanities.contains(name)) {
            throw new IllegalArgumentException("메뉴의 이름에는 비속어가 포함될 수 없다.");
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayedName that = (DisplayedName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
