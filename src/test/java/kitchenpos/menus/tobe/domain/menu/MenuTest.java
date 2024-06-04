package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.supports.domain.tobe.Price;
import kitchenpos.supports.domain.tobe.PriceFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {
  private MenuDisplayPolicy menuDisplayPolicy;

  @BeforeEach
  void setUp() {
    this.menuDisplayPolicy = new DefaultMenuDisplayPolicy();
  }

  @DisplayName("메뉴를 등록할 수 있다.")
  @Test
  void create() {
    UUID id = UUID.randomUUID();
    MenuName name = MenuFixture.normalMenuName();
    Price price = PriceFixture.normal();
    UUID menuGroupId = UUID.randomUUID();
    boolean displayed = true;
    List<MenuProduct> menuProductList = MenuFixture.normalMenuProductList(3);
    MenuProducts menuProducts = MenuFixture.createMenuProducts(menuProductList);
    Menu menu = MenuFixture.create(id, name, price, menuGroupId, displayed, menuProducts);
    assertAll(
        () -> assertThat(menu.getId()).isEqualTo(id),
        () -> assertThat(menu.getName()).isEqualTo(name),
        () -> assertThat(menu.getPrice()).isEqualTo(price),
        () -> assertThat(menu.getMenuGroupId()).isEqualTo(menuGroupId),
        () -> assertThat(menu.isDisplayed()).isEqualTo(displayed),
        () -> assertThat(menu.getMenuProducts()).isEqualTo(menuProducts)
    );
  }

  @DisplayName("메뉴 판매 조건을 만족하지 않으면 메뉴를 등록할 수 없다.")
  @Test
  void createFail() {
    UUID id = UUID.randomUUID();
    MenuName name = MenuFixture.normalMenuName();
    Price price = PriceFixture.create(new BigDecimal(10_000));
    UUID menuGroupId = UUID.randomUUID();
    boolean displayed = true;
    List<MenuProduct> menuProductList = MenuFixture.normalMenuProductList(3);
    MenuProducts menuProducts = MenuFixture.createMenuProducts(menuProductList);
    assertThatIllegalStateException()
        .isThrownBy(
            () -> MenuFixture.create(id, name, price, menuGroupId, displayed, menuProducts));
  }

  @DisplayName("메뉴 가격을 변경할 수 있다.")
  @Test
  void changePrice() {
    Price expected = PriceFixture.create(new BigDecimal(5_000));
    Menu menu = MenuFixture.normal();
    menu.changePrice(expected, menuDisplayPolicy);
    assertThat(menu.getPrice()).isEqualTo(expected);
  }

  @DisplayName("메뉴 판매 조건을 만족하지 않으면 메뉴 가격을 변경할 수 없다.")
  @Test
  void changePriceFail() {
    Price expected = PriceFixture.create(new BigDecimal(100_000));
    Menu menu = MenuFixture.normal();
    assertThatIllegalStateException()
        .isThrownBy(() -> menu.changePrice(expected, menuDisplayPolicy));
  }

  @DisplayName("메뉴를 공개할 수 있다.")
  @Test
  void display() {
    Menu menu = MenuFixture.normal();
    menu.display(menuDisplayPolicy);
    assertThat(menu.isDisplayed()).isTrue();
  }

  @DisplayName("메뉴를 비공개할 수 있다.")
  @Test
  void hide() {
    Menu menu = MenuFixture.normal();
    menu.hide();
    assertThat(menu.isDisplayed()).isFalse();
  }
}
