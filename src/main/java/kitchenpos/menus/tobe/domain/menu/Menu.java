package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.supports.domain.tobe.Price;

@Table(name = "menu")
@Entity
public class Menu {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Embedded
  @Column(name = "name", nullable = false)
  private MenuName name;

  @Embedded
  @Column(name = "price", nullable = false)
  private Price price;

  @Column(name = "menu_group_id", nullable = false)
  private UUID menuGroupId;

  @Column(name = "displayed", nullable = false)
  private boolean displayed;

  @Embedded
  private MenuProducts menuProducts;

  protected Menu() {
  }

  public Menu(UUID id, MenuName name, Price price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.menuGroupId = menuGroupId;
    this.displayed = displayed;
    this.menuProducts = menuProducts;
    validateSatisfiedSellingCondition();
  }

  private void validateSatisfiedSellingCondition() {
    if(!this.price.isLessOrEqualThen(this.menuProducts.getTotalPrice())) {
      throw new IllegalArgumentException();
    }
  }

  public void changePrice(Price price) {
    this.price = price;
    validateSatisfiedSellingCondition();
  }

  public void display() {
    this.displayed = true;
    validateSatisfiedSellingCondition();
  }

  public void hide() {
    this.displayed = false;
  }

  public UUID getId() {
    return id;
  }

  public MenuName getName() {
    return name;
  }

  public Price getPrice() {
    return price;
  }

  public UUID getMenuGroupId() {
    return menuGroupId;
  }

  public boolean isDisplayed() {
    return displayed;
  }

  public MenuProducts getMenuProducts() {
    return menuProducts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Menu menu = (Menu) o;
    return Objects.equals(id, menu.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Menu{" +
        "id=" + id +
        ", name=" + name +
        ", price=" + price +
        ", menuGroupId=" + menuGroupId +
        ", displayed=" + displayed +
        ", menuProducts=" + menuProducts +
        '}';
  }
}
