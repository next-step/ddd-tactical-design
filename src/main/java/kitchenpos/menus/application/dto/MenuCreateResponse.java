package kitchenpos.menus.application.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kitchenpos.menus.domain.menu.MenuProduct;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuProducts;

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
      list.add(MenuProductCreateResponseDto.of(menuProduct));
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

  public UUID getMenuId() {
    return menuId;
  }

  public String getMenuName() {
    return menuName;
  }

  public BigDecimal getMenuPrice() {
    return menuPrice;
  }

  public MenuGroupCreationResponseDto getMenuGroupCreationResponseDto() {
    return menuGroupCreationResponseDto;
  }

  public boolean isDisplayed() {
    return displayed;
  }

  public List<MenuProductCreateResponseDto> getMenuProductCreateResponseDtos() {
    return menuProductCreateResponseDtos;
  }
}
