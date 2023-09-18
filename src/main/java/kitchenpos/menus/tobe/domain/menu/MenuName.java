package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.support.infra.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static java.util.Objects.isNull;

@Embeddable
public final class MenuName {
    @Column(name = "name", nullable = false)
    private String value;

    private MenuName() {
    }

    private MenuName(String value) {
        this.value = value;
    }

    public static MenuName create(String value, PurgomalumClient purgomalumClient) {
        if (isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException("메뉴명은 비어있을 수 없습니다.");
        }

        boolean isContainingProfanity = purgomalumClient.containsProfanity(value);
        if (isContainingProfanity) {
            throw new IllegalArgumentException("메뉴명에 비속어가 포함되어 있습니다.");
        }

        return new MenuName(value);
    }

    protected String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuName that = (MenuName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
