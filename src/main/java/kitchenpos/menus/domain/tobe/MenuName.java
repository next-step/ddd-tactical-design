package kitchenpos.menus.domain.tobe;

import kitchenpos.products.domain.tobe.BanWordFilter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {
    private static final String INVALID_NAME_INPUT = "올바른 이름을 입력해야합니다.";

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    public MenuName(String name, BanWordFilter banWordFilter) {
        validate(name, banWordFilter);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validate(String name, BanWordFilter banWordFilter) {
        if (Objects.isNull(name) || banWordFilter.containsProfanity(name)) {
            throw new IllegalArgumentException(INVALID_NAME_INPUT);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuName)) {
            return false;
        }
        MenuName menuName = (MenuName) o;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
