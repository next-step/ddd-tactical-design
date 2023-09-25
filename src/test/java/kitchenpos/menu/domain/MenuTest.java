package kitchenpos.menu.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import kitchenpos.Fixtures;
import kitchenpos.menu.tobe.domain.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MenuTest {

    @DisplayName("메뉴의 가격이 메뉴에 속한 Product Price들의 가격 합보다 크면 메뉴가 숨김 처리된다.")
    @Test
    void testCheckPriceIfMenuPriceIsMoreThanSumOfMenuProductPrices() {
        // given
        Menu menu = Fixtures.menu(100_000, true, Fixtures.menuProduct(Fixtures.product(100_000), 1));
        menu.setPrice(BigDecimal.valueOf(100_001L)); // menu 혹은 menu가 가지는 MenuProduct의 가격 변경이 어려워 setter로 가격 변경 (테스트를 위한 메서드 같아서 꺼려짐)

        // when
        menu.checkPrice();

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 Product Price들의 가격 합이 작거나 같으면 메뉴는 처음 노출 상태를 유지한다.")
    @CsvSource(value = {"true;true", "false;false"}, delimiter = ';')
    @ParameterizedTest
    void testCheckPriceIfMenuPriceIsLessThanSumOfMenuProductPrices(boolean displayed, boolean expected) {
        // given
        Menu menu = Fixtures.menu(10_000, displayed, Fixtures.menuProduct());

        // when
        menu.checkPrice();

        // then
        assertThat(menu.isDisplayed()).isEqualTo(expected);
    }
}
