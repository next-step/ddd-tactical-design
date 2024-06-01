package kitchenpos.domain.support;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Name {
    @Column(name = "name", nullable = false)
    private String name;

    protected Name() {
    }

    public Name(String name) {
        this(name, (text) -> false);
    }

    public Name(String name, BlackWordClient blackWordClient) {
        validateName(name, blackWordClient);
        this.name = name;
    }

    private void validateName(String name, BlackWordClient blackWordClient) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("name은 Null 일 수 없습니다.");
        }
        if (blackWordClient.containsBlackWord(name)) {
            throw new IllegalArgumentException("name은 비속어가 포함될 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name menuName = (Name) o;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
