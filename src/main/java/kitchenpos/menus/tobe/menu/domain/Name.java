package kitchenpos.menus.tobe.menu.domain;

import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {
    @Column(name = "name", nullable = false)
    private String name;

    protected Name() {
    }

    public Name(final String name) {
        this(name, new DefaultProfanities());
    }

    public Name(final String name, Profanities profanities) {
        verify(name, profanities);
        this.name = name;
    }

    private void verify(final String name, Profanities profanities) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
        }

        if (profanities.contains(name)) {
            throw new IllegalArgumentException("이름에 비속어는 포함될 수 없습니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
