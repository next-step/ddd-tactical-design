package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.common.domain.FakeProfanity;
import kitchenpos.common.domain.Profanity;
import kitchenpos.common.domain.vo.DisplayedName;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.menu.tobe.domain.exception.InvalidMenuPriceException;
import kitchenpos.menus.menu.tobe.domain.vo.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    private Profanity profanity;
    private MenuProduct menuProduct;
    private DisplayedName displayedName;

    @BeforeEach
    void setUp() {
        profanity = new FakeProfanity();
        displayedName = DisplayedName.valueOf("메뉴", profanity);
        menuProduct = MenuProduct.create(UUID.randomUUID(), Price.valueOf(10_000L), Quantity.valueOf(1L));
    }

    @DisplayName("메뉴를 생성한다.")
    @Nested
    class CreateTest {
        @DisplayName("성공")
        @Test
        void success() {
            final Menu menu = Menu.create(displayedName, Price.valueOf(10_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct));

            assertAll(
                    () -> assertThat(menu.id()).isNotNull(),
                    () -> assertThat(menu.displayedName().value()).isEqualTo("메뉴"),
                    () -> assertThat(menu.price()).isEqualTo(Price.valueOf(10_000L)),
                    () -> assertThat(menu.menuGroupId()).isNotNull(),
                    () -> assertThat(menu.isDisplayed()).isTrue(),
                    () -> assertThat(menu.menuProducts().values()).contains(menuProduct)
            );
        }

        @DisplayName("가격은 메뉴상품의 금액 총합보다 적거나 같아야한다.")
        @Test
        void error_5() {
            assertThatThrownBy(() -> Menu.create(displayedName, Price.valueOf(50_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct)))
                    .isInstanceOf(InvalidMenuPriceException.class)
                    .hasMessage("가격 정보는 메뉴상품 금액의 총합보다 적거나 같아야합니다. price=50000, totalAmount=10000");
        }
    }

    @DisplayName("메뉴의 가격을 변경한다.")
    @Nested
    class ChangePrice {
        @DisplayName("성공")
        @Test
        void success() {
            final Menu menu = Menu.create(displayedName, Price.valueOf(10_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct));

            menu.changePrice(Price.valueOf(8_000L));

            assertThat(menu.price()).isEqualTo(Price.valueOf(8_000L));
        }

        @DisplayName("변경하려는 가격이 비어있을 수 없다.")
        @Test
        void error_1() {
            final Menu menu = Menu.create(displayedName, Price.valueOf(10_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct));

            assertThatThrownBy(() -> menu.changePrice(null)).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("변경하려는 가격은 메뉴상품의 금액 총합보다 적거나 같아야한다.")
        @Test
        void error_2() {
            final Menu menu = Menu.create(displayedName, Price.valueOf(10_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct));

            assertThatThrownBy(() -> menu.changePrice(Price.valueOf(50_000L)))
                    .isInstanceOf(InvalidMenuPriceException.class)
                    .hasMessage("가격 정보는 메뉴상품 금액의 총합보다 적거나 같아야합니다. price=50000, totalAmount=10000");
        }
    }

    @DisplayName("메뉴를 전시한다.")
    @Nested
    class ShowTest {

        @DisplayName("성공")
        @Test
        void success() {
            final Menu menu = Menu.create(displayedName, Price.valueOf(10_000L), UUID.randomUUID(), false, MenuProducts.of(menuProduct));

            menu.show();

            assertThat(menu.isDisplayed()).isTrue();
        }

        @DisplayName("메뉴 가격이 메뉴 상품의 금액 총합보다 높을 경우 메뉴를 전시할 수 없다.")
        @Test
        void error() {
            final UUID menuProductId = UUID.randomUUID();
            final MenuProduct menuProduct = MenuProduct.create(menuProductId, Price.valueOf(10_000L), Quantity.valueOf(1L));
            final Menu menu = Menu.create(displayedName, Price.valueOf(10_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct));
            menu.changeMenuProductPrice(menuProductId, Price.valueOf(8_000L));

            assertThatThrownBy(() -> menu.show()).isInstanceOf(IllegalStateException.class);
        }
    }

    @DisplayName("메뉴를 숨긴다.")
    @Test
    void hide() {
        final Menu menu = Menu.create(displayedName, Price.valueOf(10_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct));

        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴상품의 가격을 변경한다.")
    @Nested
    class ChangedProductPriceTest {

        @DisplayName("성공")
        @Test
        void success() {
            final UUID menuProductId = UUID.randomUUID();
            final MenuProduct menuProduct = MenuProduct.create(menuProductId, Price.valueOf(10_000L), Quantity.valueOf(1L));
            final Menu menu = Menu.create(displayedName, Price.valueOf(10_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct));

            menu.changeMenuProductPrice(menuProductId, Price.valueOf(80_000L));

            assertThat(menu.menuProducts().totalAmount()).isEqualTo(Price.valueOf(80_000L));
        }

        @DisplayName("메뉴 가격이 메뉴 상품목록의 금액 총합보다 크면 메뉴는 숨겨진다.")
        @Test
        void changeAndHide() {
            final UUID menuProductId = UUID.randomUUID();
            final MenuProduct menuProduct = MenuProduct.create(menuProductId, Price.valueOf(10_000L), Quantity.valueOf(1L));
            final Menu menu = Menu.create(displayedName, Price.valueOf(10_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct));

            menu.changeMenuProductPrice(menuProductId, Price.valueOf(8_000L));

            assertAll(
                    () -> assertThat(menu.menuProducts().totalAmount()).isEqualTo(Price.valueOf(8_000L)),
                    () -> assertThat(menu.isDisplayed()).isFalse()
            );
        }
    }
}
