package kitchenpos.menus.tobe.domain;

import kitchenpos.support.infra.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static java.util.Objects.isNull;

@Embeddable
public final class MenuName {
    @Column(name = "name", nullable = false)
    private String value;

    protected MenuName() {
    }

    public static MenuName create(String value, PurgomalumClient purgomalumClient) {
        if (isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException("상품명은 비어있을 수 없습니다.");
        }

        boolean isContainingProfanity = purgomalumClient.containsProfanity(value);
        if (isContainingProfanity) {
            throw new IllegalArgumentException("상품명에 비속어가 포함되어 있습니다.");
        }

        MenuName menuName = new MenuName();
        menuName.value = value;
        return menuName;
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
