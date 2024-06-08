package kitchenpos.menus.domain.tobe.menuproduct;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import kitchenpos.fixture.MenuProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("MenuProduct")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuProductTest {

    @Test
    void 메뉴상품의_가격과_수량을_곱하여_합을_구할_수_있다() {
        MenuProduct actual = MenuProductFixture.createFired(2);

        assertThat(actual.calculateSum()).isEqualTo(BigDecimal.valueOf(40_000L));
    }
}