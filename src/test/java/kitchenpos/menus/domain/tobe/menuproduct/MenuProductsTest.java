package kitchenpos.menus.domain.tobe.menuproduct;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.fixture.MenuProductFixture;
import kitchenpos.menus.domain.tobe.menu.MenuPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("MenuProducts")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuProductsTest {

    @Test
    void 메뉴상품들이_null일_경우_예외를_던진다() {
        assertThatThrownBy(
                () -> new MenuProducts(null, new MenuPrice(BigDecimal.valueOf(50_000L))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴상품들이_비어있을_경우_예외를_던진다() {
        assertThatThrownBy(
                () -> new MenuProducts(List.of(), new MenuPrice(BigDecimal.valueOf(50_000L))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴가격이_메뉴상품들의_가격합보다_클_경우_예외를_던진다() {
        MenuProduct fired = MenuProductFixture.createFired(1);
        MenuProduct seasoned = MenuProductFixture.createSeasoned(1);

        assertThatThrownBy(() -> new MenuProducts(List.of(fired, seasoned),
                new MenuPrice(BigDecimal.valueOf(50_000L))))
                .isInstanceOf(IllegalArgumentException.class);
    }
}