package kitchenpos.menus.tobe.application.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;

public class MenuCreateResponse {
  private final UUID menuId;
  private final String menuName;
  private final BigDecimal menuPrice;
  private final MenuGroupCreationResponseDto menuGroupCreationResponseDto;
  private final boolean displayed;
  private final List<MenuProductCreateResponseDto> menuProductCreateResponseDtos;

  public MenuCreateResponse(
      final UUID menuId,
      final String menuName,
      final BigDecimal menuPrice,
      final MenuGroupCreationResponseDto menuGroupCreationResponseDto,
      final boolean displayed,
      final List<MenuProductCreateResponseDto> menuProductCreateResponseDtos) {
    this.menuId = menuId;
    this.menuName = menuName;
    this.menuPrice = menuPrice;
    this.menuGroupCreationResponseDto = menuGroupCreationResponseDto;
    this.displayed = displayed;
    this.menuProductCreateResponseDtos = menuProductCreateResponseDtos;
  }

  public static MenuCreateResponse of(Menu menu) {
    final MenuProducts menuProducts = menu.getMenuProducts();
    final List<MenuProductCreateResponseDto> list = new ArrayList<>();

    for (MenuProduct menuProduct : menuProducts.getMenuProducts()) {
      MenuProductCreateResponseDto.of(menuProduct);
    }

    final MenuGroupCreationResponseDto menuGroupCreationResponseDto =
        MenuGroupCreationResponseDto.of(menu.getMenuGroup());

    return new MenuCreateResponse(
        menu.getId(),
        menu.getName(),
        menu.getPrice(),
        menuGroupCreationResponseDto,
        menu.isDisplayed(),
        list);
  }
}
