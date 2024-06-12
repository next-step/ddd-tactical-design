package kitchenpos.eatinorders.domain.ordertable;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class CustomerHeadcount {
  private static final long ZERO = 0L;
  private Long headCounts;

  protected CustomerHeadcount() {
  }

  private CustomerHeadcount(Long headCounts) {
    validate(headCounts);

    this.headCounts = headCounts;
  }

  public static CustomerHeadcount of(Long headCounts) {
    return new CustomerHeadcount(headCounts);
  }

  public static CustomerHeadcount zero() {
    return new CustomerHeadcount(ZERO);
  }
  private void validate(Long headCounts) {
    if (Objects.isNull(headCounts)) {
      throw new IllegalArgumentException("방문한 손님 수가 올바르지 않으면 변경할 수 없다.");
    }

    if (headCounts.compareTo(ZERO) <= 0) {
      throw new IllegalArgumentException("방문한 손님 수는 0 이상이어야 한다.");
    }
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
