package kitchenpos.menus.domain.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import kitchenpos.ToBeFixtures;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {

    @DisplayName("메뉴 상품 일급 컬렉션을 생성할 수 있다.")
    @Test
    void 생성() {
        assertDoesNotThrow(
            () -> ToBeFixtures.menuProducts()
        );
    }

    @DisplayName("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void 생성2() {
        assertThatThrownBy(
            () -> new MenuProducts(Collections.emptyList())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 상품 금액의 합을 계산할 수 있다.")
    @Test
    void 금액의합() {
        final MenuProduct menuProduct1 = ToBeFixtures.menuProduct(
            new ProductId(UUID.randomUUID()),
            new Price(BigDecimal.valueOf(16_000L)),
            2
        );
        final MenuProduct menuProduct2 = ToBeFixtures.menuProduct(
            new ProductId(UUID.randomUUID()),
            new Price(BigDecimal.valueOf(18_000L)),
            1
        );

        final MenuProducts menuProducts = new MenuProducts(
            Arrays.asList(menuProduct1, menuProduct2)
        );
        final Price actualAmount = menuProducts.getAmount();

        assertThat(actualAmount).isEqualTo(new Price(BigDecimal.valueOf(50_000L)));
    }
}
