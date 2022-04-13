package kitchenpos.products.tobe.domain;


import java.math.BigDecimal;
import java.util.UUID;

public class Product {
  private UUID productId;

  private ProductName productName;

  private ProductPrice productPrice;

  protected Product() {/*no-op*/}

  public Product(UUID productId, ProductName productName, ProductPrice productPrice) {
    this.productId = productId;
    this.productName = productName;
    this.productPrice = productPrice;
  }

  public Product(UUID productId, ProductName name, BigDecimal price) {
    this(productId, name, new ProductPrice(price));
  }

  public void changePrice(BigDecimal price) {
    this.productPrice = new ProductPrice(price);
  }

  public UUID getId() {
    return productId;
  }

  public ProductName getName() {
    return productName;
  }

  public ProductPrice getPrice() {
    return productPrice;
  }
}
