package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class DisplayedName {
    @Column(name = "name", nullable = false)
    private String name;

    protected DisplayedName() {
        // JPA requires a default constructor
    }

    public DisplayedName(final String name, final ProfanitiesChecker purgomalumClient) {
        if (Objects.isNull(name) || name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("올바르지 못한 이름을 사용할 수 없습니다.");
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
        DisplayedName that = (DisplayedName) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getName());
    }
}
