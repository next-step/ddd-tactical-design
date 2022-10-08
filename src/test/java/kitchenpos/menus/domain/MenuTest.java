package kitchenpos.menus.domain;

import static kitchenpos.menus.MenuFixtures.menu;
import static kitchenpos.menus.MenuFixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;
import kitchenpos.menus.exception.InvalidMenuPriceException;
import kitchenpos.profanity.infra.FakeProfanityCheckClient;
import kitchenpos.profanity.infra.ProfanityCheckClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MenuTest {

    private ProfanityCheckClient profanityCheckClient;

    @BeforeEach
    void setUp() {
        profanityCheckClient = new FakeProfanityCheckClient();
    }

    @DisplayName("메뉴의 가격이 상품 가격들의 합보다 클 경우 메뉴를 생성할 수 없다.")
    @Test
    void menuPriceException() {
        MenuProduct menuProduct = new MenuProduct(
            UUID.randomUUID(),
            new MenuProductPrice(BigDecimal.valueOf(20_000L)),
            new MenuProductQuantity(3)
        );

        assertThatThrownBy(() -> new Menu(
                new MenuName("반반치킨", profanityCheckClient),
                new MenuPrice(BigDecimal.valueOf(60_001L)),
                new MenuGroup(new MenuGroupName("치킨")),
                new MenuProducts(Collections.singletonList(menuProduct))
            )
        ).isExactlyInstanceOf(InvalidMenuPriceException.class);
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    void hide() {
        Menu menu = menu();
        menu.hide();
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴 전시")
    @Nested
    class Display {

        @DisplayName("메뉴를 전시할 수 있다.")
        @Test
        void display() {
            Menu menu = menu();
            menu.display();
            assertThat(menu.isDisplayed()).isTrue();
        }

        @DisplayName("메뉴의 가격이 올바르지 않을 경우 전시할 수 없다.")
        @Test
        void invalidMenuPriceException() {
            Menu menu = menu(
                10_000,
                menuProduct(5_000, 2)
            );
            menu.setPrice(BigDecimal.valueOf(12_000));
            assertThatThrownBy(menu::display)
                .isExactlyInstanceOf(InvalidMenuPriceException.class);
        }
    }
}
