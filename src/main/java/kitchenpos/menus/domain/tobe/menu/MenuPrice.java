package kitchenpos.menus.domain.tobe.menu;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
  private BigDecimal price;

  protected MenuPrice() {
  }

  private MenuPrice(Long price) {
    validate(price);
    this.price = BigDecimal.valueOf(price);
  }

  public static MenuPrice of(Long price) {
    return new MenuPrice(price);
  }
  public static MenuPrice of(BigDecimal price) {
    return new MenuPrice(price.longValue());
  }
  private void validate(Long price) {
    if (Objects.isNull(price)) {
      throw new IllegalArgumentException("메뉴의 가격이 올바르지 않으면 등록할 수 없다.");
    }
    if (price.compareTo(BigDecimal.ZERO.longValue()) < 0) {
      throw new IllegalArgumentException("메뉴의 가격은 0원 이상이어야 한다.");
    }
  }
  public BigDecimal getPrice() {
    return price;
  }

  public boolean isBigger(BigDecimal price){
    return getPrice().compareTo(price) > 0;
  }
}
