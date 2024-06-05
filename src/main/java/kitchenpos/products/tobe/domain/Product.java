package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import kitchenpos.supports.domain.tobe.Price;

@Table(name = "product")
@Entity
public class Product {

  @Id
  @Column(name = "id", columnDefinition = "binary(16)")
  private UUID id;

  @Embedded
  @Column(name = "name", nullable = false)
  private ProductName name;

  @Embedded
  @Column(name = "price", nullable = false)
  private Price price;

  protected Product() {
  }

  public Product(ProductName name, Price price) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.price = price;
  }

  public UUID getId() {
    return id;
  }

  public ProductName getName() {
    return name;
  }

  public Price getPrice() {
    return price;
  }

  public void changePrice(final Price price) {
    this.price = price;
  }
}
