package kitchenpos.menus.application.dto;

import java.util.UUID;
import kitchenpos.menus.domain.menu.MenuProduct;

public class MenuProductCreateResponseDto {
  private final UUID productId;
  private final long menuProductQuantity;

  public MenuProductCreateResponseDto(final UUID productId, final long menuProductQuantity) {
    this.productId = productId;
    this.menuProductQuantity = menuProductQuantity;
  }

  public static MenuProductCreateResponseDto of(final MenuProduct menuProduct) {
    return new MenuProductCreateResponseDto(menuProduct.getProductId(), menuProduct.getQuantity());
  }
}
