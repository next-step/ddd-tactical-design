package kitchenpos.products.tobe.domain;


import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Product {

  @Id
  @Column(name = "id", columnDefinition = "varbinary(16)")
  private UUID productId;

  @Column(name = "name", nullable = false)
  @Embedded
  private ProductName productName;

  @Column(name = "price", nullable = false)
  @Embedded
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
