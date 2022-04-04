package kitchenpos.menus.fixture;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kitchenpos.menus.stub.TestProductRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProductQuantity;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;

public class MenuFixture {

  private MenuFixture() {}

  public static Menu buildValidMenu() {
    String menuName = "테스트 메뉴";
    boolean profanity = false;
    BigDecimal price = BigDecimal.valueOf(1000L);
    MenuGroup menuGroup = new MenuGroup("메뉴그룹1");
    boolean displayed = true;
    long productId = 1L;
    List<MenuProduct> menuProducts = Arrays.asList(new MenuProduct(productId, new MenuProductQuantity(1), new TestProductRepository(productId, "메뉴상품", BigDecimal.valueOf(1000L))));

    return new Menu(menuName, profanity, price, menuGroup, displayed, menuProducts);
  }

  public static Menu buildValidHiddenMenu() {
    String menuName = "테스트 메뉴";
    boolean profanity = false;
    BigDecimal price = BigDecimal.valueOf(1000L);
    MenuGroup menuGroup = new MenuGroup("메뉴그룹1");
    boolean displayed = false;
    long productId = 1L;
    List<MenuProduct> menuProducts = Arrays.asList(new MenuProduct(productId, new MenuProductQuantity(1), new TestProductRepository(productId, "메뉴상품", BigDecimal.valueOf(1000L))));

    return new Menu(menuName, profanity, price, menuGroup, displayed, menuProducts);
  }

  public static Menu buildInvalidPriceHiddenMenu() {
    String menuName = "테스트 메뉴";
    boolean profanity = false;
    BigDecimal price = BigDecimal.valueOf(10000000L);
    MenuGroup menuGroup = new MenuGroup("메뉴그룹1");
    boolean displayed = false;
    long productId = 1L;
    List<MenuProduct> menuProducts = Arrays.asList(new MenuProduct(productId, new MenuProductQuantity(1), new TestProductRepository(productId, "메뉴상품", BigDecimal.valueOf(1000L))));

    return new Menu(menuName, profanity, price, menuGroup, displayed, menuProducts);
  }

  public static Menu buildEmptyMenuProductsMenu() {
    String menuName = "테스트 메뉴";
    boolean profanity = false;
    BigDecimal price = BigDecimal.valueOf(1000L);
    MenuGroup menuGroup = new MenuGroup("메뉴그룹1");
    boolean displayed = true;
    List<MenuProduct> menuProducts = Collections.emptyList();

    return new Menu(menuName, profanity, price, menuGroup, displayed, menuProducts);
  }

  public static Menu buildZeroQuantityMenuProductsMenu() {
    String menuName = "테스트 메뉴";
    boolean profanity = false;
    BigDecimal price = BigDecimal.valueOf(1000L);
    MenuGroup menuGroup = new MenuGroup("메뉴그룹1");
    boolean displayed = true;
    long productId = 1L;
    List<MenuProduct> menuProducts = Arrays.asList(new MenuProduct(productId, new MenuProductQuantity(0), new TestProductRepository(productId, "메뉴상품", BigDecimal.valueOf(1000L))));

    return new Menu(menuName, profanity, price, menuGroup, displayed, menuProducts);
  }
  public static Menu buildZeroPriceMenu() {
    String menuName = "테스트 메뉴";
    boolean profanity = false;
    BigDecimal price = BigDecimal.valueOf(0);
    MenuGroup menuGroup = new MenuGroup("메뉴그룹1");
    boolean displayed = true;
    long productId = 1L;
    List<MenuProduct> menuProducts = Arrays.asList(new MenuProduct(productId, new MenuProductQuantity(1), new TestProductRepository(productId, "메뉴상품", BigDecimal.valueOf(1000L))));

    return new Menu(menuName, profanity, price, menuGroup, displayed, menuProducts);
  }

  public static Menu buildMenuProductsAmountSumIsCheaperThanMenuPriceMenu() {
    String menuName = "테스트 메뉴";
    boolean profanity = false;
    BigDecimal price = BigDecimal.valueOf(10000);
    MenuGroup menuGroup = new MenuGroup("메뉴그룹1");
    boolean displayed = true;
    long productId = 1L;
    List<MenuProduct> menuProducts = Arrays.asList(new MenuProduct(productId, new MenuProductQuantity(1), new TestProductRepository(productId, "메뉴상품", BigDecimal.valueOf(1000L))));

    return new Menu(menuName, profanity, price, menuGroup, displayed, menuProducts);
  }

  public static Menu buildEmptyMenuGroupMenu() {
    String menuName = "테스트 메뉴";
    boolean profanity = false;
    BigDecimal price = BigDecimal.valueOf(1000L);
    MenuGroup menuGroup = null;
    boolean displayed = true;
    long productId = 1L;
    List<MenuProduct> menuProducts = Arrays.asList(new MenuProduct(productId, new MenuProductQuantity(1), new TestProductRepository(productId, "메뉴상품", BigDecimal.valueOf(1000L))));

    return new Menu(menuName, profanity, price, menuGroup, displayed, menuProducts);
  }

  public static Menu buildProfanityNameMenu() {
    String menuName = "비속어메뉴";
    boolean profanity = true;
    BigDecimal price = BigDecimal.valueOf(1000L);
    MenuGroup menuGroup = new MenuGroup("메뉴그룹1");
    boolean displayed = true;
    long productId = 1L;
    List<MenuProduct> menuProducts = Arrays.asList(new MenuProduct(productId, new MenuProductQuantity(1), new TestProductRepository(productId, "메뉴상품", BigDecimal.valueOf(1000L))));

    return new Menu(menuName, profanity, price, menuGroup, displayed, menuProducts);
  }


}
