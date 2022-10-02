package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.menus.menu.tobe.domain.exception.InvalidMenuException;
import kitchenpos.menus.menu.tobe.domain.vo.MenuName;
import kitchenpos.menus.menu.tobe.domain.vo.Price;
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

    @DisplayName("메뉴를 생성한다.")
    @Nested
    class CreateTest {
        private Profanity profanity;
        private MenuName menuName;
        private MenuProduct menuProduct;

        @BeforeEach
        void setUp() {
            profanity = new FakeProfanity();
            menuName = MenuName.valueOf("메뉴", profanity);
            menuProduct = MenuProduct.create(UUID.randomUUID(), Price.valueOf(10_000L), Quantity.valueOf(1L));
        }

        @DisplayName("성공")
        @Test
        void success() {
            final Menu menu = Menu.create(menuName, Price.valueOf(10_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct));

            assertAll(
                    () -> assertThat(menu.id()).isNotNull(),
                    () -> assertThat(menu.menuName().value()).isEqualTo("메뉴"),
                    () -> assertThat(menu.price()).isEqualTo(Price.valueOf(10_000L)),
                    () -> assertThat(menu.menuGroupId()).isNotNull(),
                    () -> assertThat(menu.isDisplayed()).isTrue(),
                    () -> assertThat(menu.menuProducts().values()).contains(menuProduct)
            );
        }

        @DisplayName("메뉴그룹에 포함되어야 한다.")
        @Test
        void error_1() {
            assertThatThrownBy(() -> Menu.create(menuName, Price.valueOf(10_000L), null, true, MenuProducts.of(menuProduct)))
                    .isInstanceOf(InvalidMenuException.class)
                    .hasMessage("메뉴그룹 정보가 있어야 합니다.");
        }

        @DisplayName("메뉴명 정보가 있어야 한다.")
        @Test
        void error_2() {
            assertThatThrownBy(() -> Menu.create(null, Price.valueOf(10_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct)))
                    .isInstanceOf(InvalidMenuException.class)
                    .hasMessage("메뉴명 정보가 있어야 합니다.");
        }

        @DisplayName("가격정보가 있어야 한다.")
        @Test
        void error_3() {
            assertThatThrownBy(() -> Menu.create(menuName, null, UUID.randomUUID(), true, MenuProducts.of(menuProduct)))
                    .isInstanceOf(InvalidMenuException.class)
                    .hasMessage("가격정보가 있어야 합니다.");
        }

        @DisplayName("메뉴상품 목록 정보가 있어야 합니다.")
        @Test
        void error_4() {
            assertThatThrownBy(() -> Menu.create(menuName, null, UUID.randomUUID(), true, MenuProducts.of(menuProduct)))
                    .isInstanceOf(InvalidMenuException.class)
                    .hasMessage("가격정보가 있어야 합니다.");
        }

        @DisplayName("가격은 메뉴상품의 금액 총합보다 적거나 같아야한다.")
        @Test
        void error_5() {
            assertThatThrownBy(() -> Menu.create(menuName, Price.valueOf(50_000L), UUID.randomUUID(), true, MenuProducts.of(menuProduct)))
                    .isInstanceOf(InvalidMenuException.class)
                    .hasMessage("가격 정보는 메뉴상품 금액의 총합보다 적거나 같아야합니다. price=50000, totalAmount=10000");
        }
    }
}
