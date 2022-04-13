package kitchenpos.support.domain;

import java.util.Arrays;
import java.util.Objects;

public abstract class Value {
  @Override
  public int hashCode() {
    return Objects.hash(
      Arrays.stream(getClass().getDeclaredFields())
        .map(
          field -> {
            field.setAccessible(true);
            try {
              return field.get(this);
            } catch (IllegalAccessException e) {
              e.printStackTrace();
              return null;
            }
          }
        ).toArray()
    );
  }

  @Override
  public boolean equals(Object obj) {
    if ( obj == null || getClass() != obj.getClass()) return false;
    return Arrays.stream(getClass().getDeclaredFields())
      .allMatch(field -> {
        field.setAccessible(true);
        try {
          return Objects.equals(field.get(this), field.get(obj));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
          return false;
        }
      });
  }
}
