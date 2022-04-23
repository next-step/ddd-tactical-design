package kitchenpos.menus.tobe.domain.vo;

import java.util.Objects;

public class DisplayedName {

    private final String name;

    private final Profanities profanities;

    public DisplayedName(final String name, final Profanities profanities) {
        this.name = name;
        this.profanities = profanities;
        validation();
    }

    private void validation() {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("메뉴의 이름은 비어 있을 수 없습니다.");
        }
        if (profanities.contains(name)) {
            throw new IllegalArgumentException("메뉴의 이름은 비속어를 포함할 수 없습니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DisplayedName that = (DisplayedName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}

