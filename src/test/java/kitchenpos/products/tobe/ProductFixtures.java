package kitchenpos.products.tobe;

import java.math.BigDecimal;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.PurgomalumClient;
import kitchenpos.products.tobe.application.FakePurgomalumClient;

public class ProductFixtures {

  public static Product product() {
    return product("후라이드", 16_000L, new FakePurgomalumClient());
  }

  public static Product product(
      final String name, final long price, final PurgomalumClient purgomalumClient) {
    return Product.create(name, BigDecimal.valueOf(price), purgomalumClient);
  }
}
