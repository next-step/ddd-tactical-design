package kitchenpos.products.tobe;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.FakeProductProfanityValidator;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductProfanityValidator;

public class ProductFixture {
  private static final ProductProfanityValidator profanityValidator = new FakeProductProfanityValidator();
  public static ProductPrice normalProductPrice() {
    return createProductPrice(new BigDecimal(1_000L));
  }

  public static ProductPrice createProductPrice(BigDecimal price) {
    return new ProductPrice(price);
  }

  public static ProductName normalProductName() {
    return createProductName( "상품명");
  }

  public static ProductName createProductName(String name) {
    return new ProductName(profanityValidator, name);
  }

  public static Product normal() {
    return create(normalProductName(), normalProductPrice());
  }

  public static Product create(ProductName name, ProductPrice price) {
    return new Product(name, price);
  }
}
