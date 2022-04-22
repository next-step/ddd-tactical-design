package kitchenpos.menus.tobe.menu;

import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class MenuTest {

    @Nested
    @DisplayName("메뉴 생성")
    class Constructor {
        @DisplayName("메뉴 상품이 없으면 등록할 수 없다.")
        @Test
        void test01() {
            assertThatThrownBy(() ->
                    new Menu(UUID.randomUUID(), "떡볶이", 100, true, Collections.emptyList(), UUID.randomUUID())
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("특정 메뉴 그룹에 속하지 않으면 생성할 수 없다.")
        @Test
        void name05() {
            assertThatThrownBy(() ->
                    new Menu(UUID.randomUUID(), "떡볶이", 100, true, List.of(new MenuProduct(UUID.randomUUID(), 1, BigDecimal.TEN)), null)
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("가격은 메뉴상품 목록의 금액의 합보다 적거나 같아야 한다")
        @ParameterizedTest
        @ValueSource(ints = {10, 9})
        void name03(int price) {
            assertThatCode(() ->
                    new Menu(UUID.randomUUID(), "떡볶이", price, true, List.of(new MenuProduct(UUID.randomUUID(), 1, BigDecimal.TEN)), UUID.randomUUID())
            ).doesNotThrowAnyException();
        }

        @DisplayName("가격은 메뉴상품 목록의 금액보다 클 수 없다")
        @Test
        void test05() {
            assertThatThrownBy(() ->
                    new Menu(UUID.randomUUID(), "떡볶이", 11, true, List.of(new MenuProduct(UUID.randomUUID(), 1, BigDecimal.TEN)), UUID.randomUUID())
            ).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("가격 변경")
    class changePrice {
        @DisplayName("가격을 변경할 수 있다")
        @Test
        void changePrice01() {
            Menu menu = new Menu(UUID.randomUUID(), "떡볶이", 10, true, List.of(new MenuProduct(UUID.randomUUID(), 1, BigDecimal.TEN)), UUID.randomUUID());

            menu.changePrice(9);

            assertThat(menu.getPrice()).isEqualTo(new MenuPrice(9));
        }

        @DisplayName("가격이 메뉴 상품 목록의 금액보다 크면 숨김 상태가 된다")
        @Test
        void changePrice02() {
            Menu menu = new Menu(UUID.randomUUID(), "떡볶이", 10, true, List.of(new MenuProduct(UUID.randomUUID(), 1, BigDecimal.TEN)), UUID.randomUUID());

            menu.changePrice(11);

            assertThat(menu.isDisplayed()).isFalse();
        }
    }

    @DisplayName("메뉴를 노출할 수 있다.")
    @Test
    void display01() {
        Menu menu = new Menu(UUID.randomUUID(), "떡볶이", 10, false, List.of(new MenuProduct(UUID.randomUUID(), 1, BigDecimal.TEN)), UUID.randomUUID());

        menu.display();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide01() {
        Menu menu = new Menu(UUID.randomUUID(), "떡볶이", 10, true, List.of(new MenuProduct(UUID.randomUUID(), 1, BigDecimal.TEN)), UUID.randomUUID());

        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }
}
