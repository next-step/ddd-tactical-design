package kitchenpos.products.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.common.domain.ProfanityValidator;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "name", nullable = false)
  @Embedded
  private ProductName name;

  @Column(name = "price", nullable = false)
  @Embedded
  private ProductPrice price;

  protected Product() {
  }

  private Product(ProductName name, ProductPrice price) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.price = price;
  }

  public static Product from(String name, Long price, ProfanityValidator profanityValidator) {

    return new Product(ProductName.from(name, profanityValidator), ProductPrice.from(price));
  }

  public void changeProductPrice(Long price) {
    this.price = ProductPrice.from(price);
  }

  public UUID getId() {
    return id;
  }

  public String getProductName() {
    return name.getName();
  }

  public BigDecimal getProductPrice() {
    return price.getPrice();
  }

  @Override


  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
