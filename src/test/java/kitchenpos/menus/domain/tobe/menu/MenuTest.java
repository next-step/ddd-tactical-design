package kitchenpos.menus.domain.tobe.menu;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.MenuGroupFixture;
import kitchenpos.fixture.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Menu")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuTest {

    @Test
    void 메뉴가격을_변경할_수_있다() {
        Menu actual = createFriedMenu();

        actual.changePrice(new MenuPrice(BigDecimal.valueOf(35_000L)));

        assertThat(actual.getPrice()).isEqualTo(BigDecimal.valueOf(35_000L));
    }

    @Test
    void 메뉴를_노출할_수_있다() {
        Menu actual = createFriedMenu();

        actual.display();

        assertThat(actual.isDisplayed()).isTrue();
    }

    @Test
    void 메뉴를_숨길_수_있다() {
        Menu actual = createFriedMenu();

        actual.hide();

        assertThat(actual.isDisplayed()).isFalse();
    }

    private Menu createFriedMenu() {
        return MenuFixture.createFriedOnePlusOne(MenuGroupFixture.createChicken(),
                ProductFixture.createFired());
    }
}