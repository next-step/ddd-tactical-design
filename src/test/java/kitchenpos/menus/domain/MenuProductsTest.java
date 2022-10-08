package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import kitchenpos.menus.exception.InvalidMenuProductsException;
import kitchenpos.profanity.infra.FakeProfanityCheckClient;
import kitchenpos.profanity.infra.ProfanityCheckClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {

    private ProfanityCheckClient profanityCheckClient;

    @BeforeEach
    void setUp() {
        profanityCheckClient = new FakeProfanityCheckClient();
    }

    @DisplayName("메뉴의 상품은 하나 이상이어야 한다.")
    @Test
    void emptyException() {
        assertThatThrownBy(() -> new MenuProducts(new ArrayList<>()))
            .isExactlyInstanceOf(InvalidMenuProductsException.class);
    }

    @DisplayName("메뉴의 가격은 메뉴의 상품 가격들의 합보다 작거나 같아야 한다.")
    @Test
    void menuPriceException() {
        Menu 반반치킨_3마리 = new Menu(
            new MenuName("반반치킨", profanityCheckClient),
            new MenuPrice(BigDecimal.valueOf(60_001L)),
            new MenuGroup(new MenuGroupName("치킨"))
        );

        MenuProduct menuProduct = new MenuProduct(
            반반치킨_3마리,
            UUID.randomUUID(),
            new MenuProductPrice(BigDecimal.valueOf(20_000L)),
            new MenuProductQuantity(3)
        );

        assertThatThrownBy(() -> new MenuProducts(Arrays.asList(menuProduct)))
            .isExactlyInstanceOf(InvalidMenuProductsException.class);
    }
}
