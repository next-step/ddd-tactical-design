package kitchenpos.support.vo;

import javax.persistence.Embeddable;
import java.util.Arrays;
import java.util.Objects;

@Embeddable
public abstract class ValueObject<T extends ValueObject<T>> {
    @Override
    public int hashCode() {
        return Objects.hash(
                Arrays.stream(getClass().getDeclaredFields())
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).toArray());
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        return Arrays.stream(getClass().getDeclaredFields())
                .allMatch(field -> {
                    field.setAccessible(true);
                    try {
                        return Objects.equals(field.get(this), field.get(o));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                });
    }
}
