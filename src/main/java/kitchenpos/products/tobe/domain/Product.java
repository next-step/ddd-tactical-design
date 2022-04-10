package kitchenpos.products.tobe.domain;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
  @Column(name = "id", columnDefinition = "varbinary(16)")
  @Id
  private UUID productId;

  @Embedded
  @Column(name = "name", nullable = false)
  private ProductName productName;

  @Embedded
  @Column(name = "price", nullable = false)
  private ProductPrice productPrice;

  protected Product() {/*no-op*/}

  public Product(UUID productId, ProductName productName, ProductPrice productPrice) {
    this.productId = productId;
    this.productName = productName;
    this.productPrice = productPrice;
  }

  public Product(UUID productId, String name, BigDecimal price) {
    this(productId, new ProductName(name), new ProductPrice(price));
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
