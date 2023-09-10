package kitchenpos.menus.domain.tobe.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Name {
    @Column(name = "name", nullable = false)
    private String name;

    protected Name() {
    }

    private Name(String name) {
        nullValidation(name);
        this.name = name;
    }

    public static Name of(String name) {
        return new Name(name);
    }

    public static Name of(String name, boolean containsProfanity) {
        if (containsProfanity) {
            throw new IllegalArgumentException("상품 이름에 비속어가 포함되어 있습니다.");
        }
        return new Name(name);
    }

    private void nullValidation(String name) {
        if (name == "" || Objects.isNull(name)) {
            throw new IllegalArgumentException("이름은 필수로 입력되야 합니다.");
        }
    }
}
