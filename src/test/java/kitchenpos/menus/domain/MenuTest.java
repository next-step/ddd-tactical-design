package kitchenpos.menus.domain;

import static kitchenpos.menus.MenuFixtures.menu;
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
}
