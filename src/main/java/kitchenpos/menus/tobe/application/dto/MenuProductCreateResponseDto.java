package kitchenpos.menus.tobe.application.dto;

import kitchenpos.menus.tobe.domain.menu.MenuProduct;

public class MenuProductCreateResponseDto {
  private final String menuProductName;
  private final long menuProductQuantity;

  public MenuProductCreateResponseDto(
      final String menuProductName, final long menuProductQuantity) {
    this.menuProductName = menuProductName;
    this.menuProductQuantity = menuProductQuantity;
  }

  public static MenuProductCreateResponseDto of(final MenuProduct menuProduct) {
    return new MenuProductCreateResponseDto(
        menuProduct.getMenuProductName(), menuProduct.getQuantity());
  }
}
