package kitchenpos.eatinorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class OrderDateTime {
  @Column(name = "order_date_time", nullable = false)
  private LocalDateTime orderDateTime;

  protected OrderDateTime() {}

  protected OrderDateTime(final LocalDateTime orderDateTime) {
    if (Objects.isNull(orderDateTime)) {
      throw new NullPointerException();
    }

    if (orderDateTime.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException();
    }

    this.orderDateTime = orderDateTime;
  }

  public LocalDateTime getOrderDateTime() {
    return orderDateTime;
  }
}
