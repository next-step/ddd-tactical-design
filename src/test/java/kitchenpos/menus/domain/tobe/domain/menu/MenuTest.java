package kitchenpos.menus.domain.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import kitchenpos.ToBeFixtures;
import kitchenpos.common.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MenuTest {

    @DisplayName("메뉴를 생성할 수 있다.")
    @Test
    void 생성() {
        assertDoesNotThrow(
            () -> ToBeFixtures.menu()
        );
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void 생성2() {
        assertThatThrownBy(
            () -> ToBeFixtures.menu(1_000_000L, ToBeFixtures.menuProducts())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 가격 변경 - 메뉴의 가격은 메뉴에 속한 상품 금액의 합보다 적거나 같아야 한다.")
    @Test
    void 가격변경() {
        final Menu menu = ToBeFixtures.menu();

        final Price newPrice = new Price(BigDecimal.valueOf(1_000L));
        menu.changePrice(newPrice);

        assertAll(
            () -> assertThat(menu.isDisplayed()).isTrue(),
            () -> assertThat(menu.getPrice()).isEqualTo(newPrice)
        );
    }

    @DisplayName("메뉴 가격 변경 - 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴를 숨긴다.")
    @Test
    void 가격변경2() {
        final Menu menu = ToBeFixtures.menu();

        final Price newPrice = new Price(BigDecimal.valueOf(1_000_000L));
        menu.changePrice(newPrice);

        assertAll(
            () -> assertThat(menu.isHidden()).isTrue(),
            () -> assertThat(menu.getPrice()).isEqualTo(newPrice)
        );
    }
}
