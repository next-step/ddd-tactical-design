package kitchenpos.menus.domain.tobe.menu;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuProductPrice {
  public static final int ZERO = 0;
  private BigDecimal price;

  protected MenuProductPrice() {
  }

  private MenuProductPrice(Long price) {
    validate(price);
    this.price = BigDecimal.valueOf(price);
  }

  public static MenuProductPrice of(Long price) {
    return new MenuProductPrice(price);
  }

  private void validate(Long price) {
    if (Objects.isNull(price)) {
      throw new IllegalArgumentException("메뉴 상품의 가격이 올바르지 않으면 등록할 수 없다.");
    }
    if (price.compareTo(BigDecimal.ZERO.longValue()) < ZERO) {
      throw new IllegalArgumentException("메뉴 상품의 가격은 0원 이상이어야 한다.");
    }
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal multiply(BigDecimal quantity) {
    return getPrice().multiply(quantity);
  }
}
