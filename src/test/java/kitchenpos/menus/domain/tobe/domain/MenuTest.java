package kitchenpos.menus.domain.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.menus.domain.tobe.domain.MenuFixture.menu;
import static org.assertj.core.api.Assertions.*;

class MenuTest {

    @DisplayName("메뉴 생성")
    @Nested
    class CreateTest {
        @DisplayName("이름, 가격, 메뉴 그룹 이름, 상품 목록으로 메뉴를 생성한다")
        @ParameterizedTest
        @ValueSource(strings = {"15000", "20000"})
        void create(BigDecimal price) {
            assertThatCode(() ->
                    menu("탕수육 세트", price, "요리 세트"))
                    .doesNotThrowAnyException();
        }

        @DisplayName("메뉴를 생성할 때 가격은 메뉴 상품 목록 금액의 합보다 적거나 같아야 한다")
        @Test
        void createLessThanOrEqualPrice() {
            assertThatThrownBy(() -> menu("탕수육 세트", BigDecimal.valueOf(21_000), "요리 세트"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴의 가격은 메뉴 상품 목록 금액의 합보다 적거나 같아야 합니다.");
        }
    }

    @DisplayName("메뉴 가격 변경")
    @Nested
    class ChangePriceTest {
        @DisplayName("메뉴의 가격을 변경한다")
        @Test
        void changePrice() {
            // given
            Menu menu = menu("탕수육 세트", BigDecimal.valueOf(15_000), "요리 세트");

            // when
            menu.changePrice(BigDecimal.valueOf(10_000));

            // then
            assertThat(menu.getPrice()).isEqualTo(new MenuPrice(10_000));
        }

        @DisplayName("메뉴의 가격을 변경할 때 메뉴 상품 목록 금액의 합보다 적거나 같아야 한다")
        @Test
        void changeLessThanOrEqualPrice() {
            // given
            Menu menu = menu("탕수육 세트", BigDecimal.valueOf(15_000), "요리 세트");

            // when
            menu.changePrice(BigDecimal.valueOf(21_000));

            // then
            assertThat(menu.isDisplayed()).isFalse();
        }
    }

    @DisplayName("메뉴 노출")
    @Nested
    class DisplayTest {
        @DisplayName("메뉴를 숨긴다")
        @Test
        void hide() {
            // given
            Menu menu = menu("탕수육 세트", BigDecimal.valueOf(15_000), "요리 세트");

            // when
            menu.hide();

            // then
            assertThat(menu.isDisplayed()).isFalse();
        }

        @DisplayName("메뉴를 노출한다")
        @Test
        void display() {
            // given
            Menu menu = menu("탕수육 세트", BigDecimal.valueOf(15_000), "요리 세트");

            // when
            menu.display();

            // then
            assertThat(menu.isDisplayed()).isTrue();
        }

        @DisplayName("메뉴를 노출할 때 가격은 메뉴 상품 목록 금액의 합보다 적거나 같아야 한다")
        @Test
        void displayLessThanOrEqualPrice() {
            // given
            Menu menu = menu("탕수육 세트", BigDecimal.valueOf(15_000), "요리 세트");
            menu.changePrice(BigDecimal.valueOf(21_000));

            // when, then
            assertThatThrownBy(menu::display)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴의 가격은 메뉴 상품 목록 금액의 합보다 적거나 같아야 합니다.");
        }
    }
}
