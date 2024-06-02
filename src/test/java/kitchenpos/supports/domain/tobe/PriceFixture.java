package kitchenpos.supports.domain.tobe;

import java.math.BigDecimal;

public class PriceFixture {

  public static Price normal() {
    return create(new BigDecimal(1_000L));
  }

  public static Price create(BigDecimal price) {
    return new Price(price);
  }
}
