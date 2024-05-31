package kitchenpos.menus.tobe.domain.menu;

import java.math.BigDecimal;

public class MenuFixture {
  private static final MenuProfanityValidator menuProfanityValidator = new FakeMenuProfanityValidator();
  public static MenuPrice normalMenuPrice() {
    return createMenuPrice(new BigDecimal(500));
  }

  public static MenuPrice createMenuPrice(BigDecimal price) {
    return new MenuPrice(price);
  }

  public static MenuName normalMenuName() {
    return createMenuName("메뉴명");
  }
  public static MenuName createMenuName(String name) {
    return new MenuName(menuProfanityValidator, name);
  }
}
