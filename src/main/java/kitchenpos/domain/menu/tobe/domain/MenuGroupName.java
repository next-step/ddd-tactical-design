package kitchenpos.domain.menu.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
class MenuGroupName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() {
    }

    public MenuGroupName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }
}
