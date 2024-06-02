package kitchenpos.supports.domain.tobe;

import java.math.BigDecimal;

public class QuantityFixture {

  public static Quantity normal() {
    return create(3L);
  }

  public static Quantity create(Long quantity) {
    return new Quantity(quantity);
  }
}
