package kitchenpos.menus.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

import static kitchenpos.Fixtures2.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuTest {

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> menu(25000, chickenMenuProduct(), frenchFriesMenuProduct()));
    }

    @DisplayName("상품이 없으면 등록할 수 없다.")
    @Test
    void emtpyProduct() {
        assertThatThrownBy(() -> menu(25000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 상품 정보가 존재하지 않습니다.");
    }

    @DisplayName("특정 메뉴 그룹에 속하지 않는 메뉴는 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    void emptyMenuGroup(MenuGroup menuGroup) {
        assertThatThrownBy(() -> new Menu("치킨 세트", BigDecimal.valueOf(10000), menuGroup, true, new FakePurgomalumClient(), chickenMenuProduct()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 그룹이 지정되지 않았습니다.");
    }

    @DisplayName("메뉴의 가격은 0원 보다 작을 수 없다.")
    @Test
    void negativePrice() {
        assertThatThrownBy(() -> menu(-1, chickenMenuProduct(), frenchFriesMenuProduct()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 메뉴 가격 입니다.");
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void invalidPrice() {
        assertThatThrownBy(() -> menu(30000, chickenMenuProduct(), frenchFriesMenuProduct()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 메뉴 가격 입니다.");
    }

    @DisplayName("메뉴의 이름에는 비속어가 포함될 수 없다.")
    @Test
    void invalidName() {
        assertThatThrownBy(() -> new Menu("비속어", BigDecimal.valueOf(10000), menuGroup(), true, new FakePurgomalumClient(), chickenMenuProduct()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 메뉴 이름 입니다.");
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        Menu menu = menu(10000, chickenMenuProduct(), frenchFriesMenuProduct());
        menu.changePrice(BigDecimal.valueOf(2000));

        assertThat(menu.getPrice()).isEqualTo(new MenuPrice(BigDecimal.valueOf(2000)));
    }

    @DisplayName("메뉴의 가격을 0보다 작은 값으로 변경 할 수 없다.")
    @Test
    void changeInvalidPrice() {
        Menu menu = menu(10000, chickenMenuProduct(), frenchFriesMenuProduct());
        assertThatThrownBy(() -> menu.changePrice(BigDecimal.valueOf(-1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 메뉴 가격 입니다.");
    }

    @DisplayName("메뉴를 노출시킬 수 있다.")
    @Test
    void display() {
        Menu menu = new Menu("치킨 세트", BigDecimal.valueOf(10000), menuGroup(), false, new FakePurgomalumClient(), chickenMenuProduct());
        menu.display();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        Menu menu = menu(1000, chickenMenuProduct());
        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    void doNotDisplay() {
        Product product = new Product("떡볶이", BigDecimal.valueOf(1000), new FakePurgomalumClient());
        Menu menu = new Menu("엄마 떡볶이", BigDecimal.valueOf(1000), menuGroup(), true, new FakePurgomalumClient(), new MenuProduct(product, 1));
        product.changePrice(BigDecimal.valueOf(500));

        menu.displayCheck();

        assertThat(menu.isDisplayed()).isFalse();
    }

    private Menu menu(int price, MenuProduct... menuProducts) {
        return new Menu("치킨 세트", BigDecimal.valueOf(price), menuGroup(), true, new FakePurgomalumClient(), menuProducts);
    }
}
