package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class CustomerHeadcount {
  private static final int ZERO = 0;
  private Integer headCounts;

  protected CustomerHeadcount() {
  }

  private CustomerHeadcount(Integer headCounts) {
    validate(headCounts);

    this.headCounts = headCounts;
  }

  public static CustomerHeadcount of(Integer headCounts) {
    return new CustomerHeadcount(headCounts);
  }

  public static CustomerHeadcount zero() {
    return new CustomerHeadcount(ZERO);
  }
  private void validate(Integer headCounts) {
    if (Objects.isNull(headCounts)) {
      throw new IllegalArgumentException("방문한 손님 수가 올바르지 않으면 변경할 수 없다.");
    }

    if (headCounts.compareTo(ZERO) <= 0) {
      throw new IllegalArgumentException("방문한 손님 수는 0 이상이어야 한다.");
    }
  }

  public Integer getHeadCounts() {
    return headCounts;
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
