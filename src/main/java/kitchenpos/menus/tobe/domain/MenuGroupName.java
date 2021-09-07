package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.function.Function;

@Embeddable
public class MenuGroupName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() {}

    MenuGroupName(final String name) {
        validate(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validate(final String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("메뉴 그룹 이름은 필수값입니다.");
        }
    }

    void validate(final Function<String, Boolean> validator) {
        if (validator.apply(name)) {
            throw new IllegalArgumentException("적절하지 않은 메뉴 그룹 이름입니다.");
        }
    }
}
