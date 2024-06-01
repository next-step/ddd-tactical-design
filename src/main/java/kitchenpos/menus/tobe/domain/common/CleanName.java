package kitchenpos.menus.tobe.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.menus.tobe.domain.MenuNameValidationService;

import java.util.Objects;

@Embeddable
public class CleanName {
    @Column(name = "name", nullable = false)
    private String name;

    protected CleanName() {}

    public CleanName(String name, MenuNameValidationService menuNameValidationService) {
        this(name);
        if (menuNameValidationService.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }

    public CleanName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CleanName cleanName1 = (CleanName) o;
        return Objects.equals(name, cleanName1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
