package kitchenpos.menus.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.MenuGroupFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductPrice;
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
    void 메뉴가격_변경시_상품가격x상품갯수의_총합을_넘으면_예외를_던진다() {
        Menu actual = createFriedMenu();

        assertThatThrownBy(() -> actual.changePrice(new MenuPrice(BigDecimal.valueOf(50_000L))))
                .isInstanceOf(IllegalArgumentException.class);
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

    @Test
    void 메뉴가격이_상품가격x상품갯수의_총합을_넘는지_확인할_수_있다() {
        Menu actual = createFriedMenu();

        assertThat(actual.isOverThanProductSumPrice()).isFalse();
    }

    @Test
    void 메뉴상품의_가격_수정_시_상품가격x상품갯수의_총합이_넘으면_메뉴를_숨김처리한다() {
        UUID productId = UUID.randomUUID();
        Menu menu = MenuFixture.createFriedOnePlusOne(MenuGroupFixture.createChicken(),
                new Product(productId, "후라이드", BigDecimal.valueOf(20_000L)), 30_000L);

        menu.changeMenuProductPrice(productId, new ProductPrice(BigDecimal.valueOf(10_000L)));

        assertThat(menu.isDisplayed()).isFalse();
    }

    private Menu createFriedMenu() {
        return MenuFixture.createFriedOnePlusOne(MenuGroupFixture.createChicken(),
                ProductFixture.createFired(20_000L), 30_000L);
    }
}