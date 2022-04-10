package kitchenpos.products.tobe.domain;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
  @Column(name = "id", columnDefinition = "varbinary(16)")
  @Id
  private UUID id;

  @Embedded
  @Column(name = "name", nullable = false)
  private ProductName name;

  @Embedded
  @Column(name = "price", nullable = false)
  private ProductPrice price;

  protected Product() {
  }

  public Product(UUID id, ProductName name, ProductPrice price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Product(UUID id, String name, BigDecimal price) {
    this(id, new ProductName(name), new ProductPrice(price));
  }

  public void changePrice(BigDecimal price) {
    this.price = new ProductPrice(price);
  }

  public UUID getId() {
    return id;
  }

  public ProductName getName() {
    return name;
  }

  public ProductPrice getPrice() {
    return price;
  }
}
