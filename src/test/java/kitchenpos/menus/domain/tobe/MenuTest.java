package kitchenpos.menus.domain.tobe;

import kitchenpos.products.domain.tobe.FakeProfanities;
import kitchenpos.products.domain.tobe.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    Product product;
    MenuProducts menuProducts;
    MenuGroup menuGroup;
    BigDecimal menuPrice;

    @BeforeEach
    void setUp() {
        product = Product.createProduct("후라이드", BigDecimal.valueOf(16000), new FakeProfanities());
        menuGroup = new MenuGroup(UUID.randomUUID(), "세트메뉴");
        menuProducts = new MenuProducts(Arrays.asList(MenuProduct.createMenuProduct(product.getId(), product.getPrice().getPriceValue(), 2)));
        menuPrice = BigDecimal.valueOf(19000);
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void create() {
        final Menu actual = Menu.createMenu("후라이드+후라이드", menuGroup, menuPrice, menuProducts, new FakeProfanities());

        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getDisplayedName().getName()).isEqualTo("후라이드+후라이드"),
                () -> assertThat(actual.getPrice().getPriceValue()).isEqualTo(menuPrice),
                () -> assertThat(actual.getMenuGroup().getId()).isEqualTo(menuGroup.getId()),
                () -> assertThat(actual.isDisplayed()).isEqualTo(true),
                () -> assertThat(actual.getMenuProducts().getMenuProducts()).hasSize(1)
        );
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @MethodSource("menuProducts")
    @ParameterizedTest
    void create(final List<MenuProduct> menuProducts) {
        assertThatThrownBy(() -> Menu.createMenu("후라이드+후라이드", menuGroup, menuPrice, new MenuProducts(menuProducts), new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> menuProducts() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList()),
                Arguments.of(Arrays.asList(MenuProduct.createMenuProduct(INVALID_ID, BigDecimal.valueOf(1000), 2)))
        );
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0개 이상이어야 한다.")
    @Test
    void createNegativeQuantity() {
        List<MenuProduct> requestMenuProducts = Arrays.asList(MenuProduct.createMenuProduct(product.getId(), product.getPrice().getPriceValue(), -1));

        assertThatThrownBy(() -> Menu.createMenu(
                "후라이드+후라이드",
                menuGroup,
                menuPrice,
                new MenuProducts(requestMenuProducts),
                new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        List<MenuProduct> requestMenuProducts = Arrays.asList(MenuProduct.createMenuProduct(product.getId(), product.getPrice().getPriceValue(), 2));

        assertThatThrownBy(() -> Menu.createMenu(
                "후라이드+후라이드",
                menuGroup,
                price,
                new MenuProducts(requestMenuProducts),
                new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void createExpensiveMenu() {
        List<MenuProduct> requestMenuProducts = Arrays.asList(MenuProduct.createMenuProduct(product.getId(), product.getPrice().getPriceValue(), 2));
        MenuProducts menuProducts = new MenuProducts(requestMenuProducts);

        BigDecimal expensiveMoreThanMenuProducsTotalPrice = menuProducts.totalAmount().add(BigDecimal.valueOf(1000));
        assertThatThrownBy(() -> Menu.createMenu(
                "후라이드+후라이드",
                menuGroup,
                expensiveMoreThanMenuProducsTotalPrice,
                menuProducts,
                new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @NullSource
    @ParameterizedTest
    void create(final UUID menuGroupId) {
        assertThatThrownBy(() -> Menu.createMenu("후라이드+후라이드", new MenuGroup(menuGroupId, "없음"), menuPrice, menuProducts, new FakeProfanities()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> Menu.createMenu(name, menuGroup, menuPrice, menuProducts, new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final Menu menu = Menu.createMenu("후라이드+후라이드", menuGroup, menuPrice, menuProducts, new FakeProfanities());
        BigDecimal changePrice = BigDecimal.valueOf(10000);

        menu.changePrice(Price.createPrice(changePrice));

        assertThat(menu.getPrice().getPriceValue()).isEqualTo(changePrice);
    }


    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(final BigDecimal price) {
        final Menu menu = Menu.createMenu("후라이드+후라이드", menuGroup, menuPrice, menuProducts, new FakeProfanities());

        assertThatThrownBy(() -> menu.changePrice(Price.createPrice(price)))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display() {
        final Menu menu = Menu.createMenu("후라이드+후라이드", menuGroup, menuPrice, menuProducts, new FakeProfanities());
        menu.displayMenu();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void displayExpensiveMenu() {
        final Menu menu = Menu.createMenu("후라이드+후라이드", menuGroup, menuPrice, menuProducts, new FakeProfanities());
        final BigDecimal totalPrice = menuProducts.totalAmount().add(BigDecimal.valueOf(1000));

        menu.changePrice(Price.createPrice(totalPrice));

        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        final Menu menu = Menu.createMenu("후라이드+후라이드", menuGroup, menuPrice, menuProducts, new FakeProfanities());
        menu.hideMenu();

        assertThat(menu.isDisplayed()).isFalse();
    }
}
