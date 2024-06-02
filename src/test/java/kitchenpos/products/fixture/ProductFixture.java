package kitchenpos.products.fixture;

import kitchenpos.products.application.FakeProfanityValidator;
import kitchenpos.products.domain.tobe.Product;

public class ProductFixture {

  public static Product product = createProduct();

  public static Product createProduct() {
    return Product.from("우동", 10_000L, new FakeProfanityValidator());
  }

  public static Product createProduct(String productName) {
    return Product.from(productName, 10_000L, new FakeProfanityValidator());
  }
}
