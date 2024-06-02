package kitchenpos.products.tobe;

import kitchenpos.products.tobe.domain.FakeProductProfanityValidator;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductProfanityValidator;
import kitchenpos.supports.domain.tobe.Price;
import kitchenpos.supports.domain.tobe.PriceFixture;

public class ProductFixture {

  private static final ProductProfanityValidator profanityValidator = new FakeProductProfanityValidator();

  public static ProductName normalProductName() {
    return createProductName("상품명");
  }

  public static ProductName createProductName(String name) {
    return new ProductName(profanityValidator, name);
  }

  public static Product normal() {
    return create(normalProductName(), PriceFixture.normal());
  }

  public static Product create(ProductName name, Price price) {
    return new Product(name, price);
  }
}
