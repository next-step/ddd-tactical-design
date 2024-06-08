package kitchenpos.eatinorders.domain.ordertable;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CustomerHeadcount {
  private Long headCounts;

  protected CustomerHeadcount() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerHeadcount that = (CustomerHeadcount) o;
    return Objects.equals(headCounts, that.headCounts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(headCounts);
  }
}
