package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.function.Predicate;

@Table(name = "displayed_name")
@Entity
public class DisplayedName {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "name", nullable = false)
    private String name;

    public DisplayedName() {
    }

    public DisplayedName(final String name, final Predicate<String> profanityValidator) {
        validate(name, profanityValidator);
        this.name = name;
    }

    private void validate(final String name, final Predicate<String> profanityValidator) {
        if (Objects.isNull(name) || profanityValidator.test(name)) {
            throw new IllegalArgumentException();
        }
    }

    public String display() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final DisplayedName that = (DisplayedName) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
