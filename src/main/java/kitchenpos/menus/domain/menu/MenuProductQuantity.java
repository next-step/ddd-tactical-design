package kitchenpos.menus.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MenuProductQuantity {
  @Column(name = "quantity", nullable = false)
  private Long quantity;

  protected MenuProductQuantity() {}

  protected MenuProductQuantity(Long quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("수량이 0보다 작을 수 없습니다.");
    }

    this.quantity = quantity;
  }

  public Long getQuantity() {
    return quantity;
  }
}
