package kitchenpos.menus.tobe.domain;

import kitchenpos.NewFixtures;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.products.application.FakeDisplayNameChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.menus.exception.MenuExceptionMessage.MENU_PRICE_MORE_PRODUCTS_SUM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("메뉴테스트")
public class NewMenuTest {

    private final NewMenuGroup menuGroup = NewFixtures.menuGroup("두마리치킨메뉴그룹");
    private final NewMenuProduct menuProduct1 = NewFixtures.menuProduct(NewFixtures.newProduct(14_000L), 1L);
    private final NewMenuProduct menuProduct2 = NewFixtures.menuProduct(NewFixtures.newProduct(16_000L), 1L);
    private final FakeDisplayNameChecker displayNameChecker = new FakeDisplayNameChecker();

    @DisplayName("메뉴 생성 성공")
    @Test
    void create() {
        UUID id = UUID.randomUUID();
        NewMenu menu = createMenu(id, 30_000L, false);

        assertThat(menu).isEqualTo(createMenu(id, 30_000L, false));
    }

    @DisplayName("메뉴의 가격이 메뉴상품 가격들의 합보다 크면 예외를 반환한다.")
    @Test
    void create_failed() {
        UUID id = UUID.randomUUID();
        assertThatThrownBy(() -> createMenu(id, 50_000L, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MENU_PRICE_MORE_PRODUCTS_SUM);
    }

    @DisplayName("메뉴를 노출한다.")
    @Test
    void display() {
        NewMenu menu = createMenu(UUID.randomUUID(), 30_000L, false);

        menu.displayed();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 비노출한다.")
    @Test
    void hide() {
        NewMenu menu = createMenu(UUID.randomUUID(), 30_000L, true);

        menu.notDisplayed();

        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴 가격을 변경한다.")
    @Test
    void changePrice() {
        NewMenu menu = createMenu(UUID.randomUUID(), 30_000L, true);

        Price changePrice = Price.of(BigDecimal.valueOf(20_000L));
        menu.changePrice(changePrice);

        assertThat(menu.getPrice()).isEqualTo(changePrice);
    }

    @DisplayName("메뉴 가격이 메뉴 상품 가격들의 합보다 크면 예외를 반환한다.")
    @Test
    void changePrice_failed() {
        NewMenu menu = createMenu(UUID.randomUUID(), 30_000L, true);

        Price changePrice = Price.of(BigDecimal.valueOf(50_000L));

        assertThatThrownBy( () -> menu.changePrice(changePrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MENU_PRICE_MORE_PRODUCTS_SUM);
    }

    private NewMenu createMenu(UUID id, Long price, boolean isDisplayed) {
        return NewMenu.create(
                id,
                menuGroup.getId(),
                DisplayedName.of("두마리치킨메뉴", displayNameChecker),
                Price.of(BigDecimal.valueOf(price)),
                MenuProducts.create(List.of(menuProduct1, menuProduct2)),
                isDisplayed
        );
    }
}
