package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "product")
public class Product {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Embedded
  private DisplayedName displayedName;

  @Embedded
  private ProductPrice price;

  public Product() {
  }

  public Product(String name, boolean isProfanity, BigDecimal price) {
    this.id = UUID.randomUUID();
    this.displayedName = new DisplayedName(name, isProfanity);
    this.price = new ProductPrice(price);
  }

  public void changePrice(BigDecimal price) {
    this.price = new ProductPrice(price);
  }

  public UUID getId() {
    return id;
  }

  public DisplayedName getDisplayedName() {
    return displayedName;
  }

  public ProductPrice getPrice() {
    return price;
  }
}
