package kitchenpos.menus.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuProductsTest {

    @DisplayName("메뉴 상품 목록의 총합 금액을 계산할 수 있다.")
    @CsvSource(value = {"1_000:1:1_000:1:2_000", "2_000:1:2_000:2:6_000", "1_000:3:3_000:2:9_000"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 첫 번째 메뉴 상품 가격 : `{0}`, 첫 번째 메뉴 상품 수량 : `{1}`, 
                두 번째 메뉴 상품 가격 : `{2}`, 두 번째 메뉴 상품 수량 : `{3}`
                메뉴 상품 목록의 총합 금액 : `{4}`
            """)
    void totalAmount(final long firstPrice, final long firstQuantity, final long secondPrice, final long secondQuantity, final long expected) {
        final UUID menuId = UUID.randomUUID();
        final MenuProducts menuProducts = new MenuProducts(
                List.of(
                        new MenuProduct(1L, firstPrice, firstQuantity, menuId, 1L),
                        new MenuProduct(2L, secondPrice, secondQuantity, menuId, 2L)
                )
        );
        assertThat(menuProducts.totalAmount()).isEqualTo(expected);
    }

    @DisplayName("메뉴 상품 목록의 가격을 변경할 수 있다.")
    @CsvSource(value = {"1_000:1:1_001:1_001", "2_000:2:2_001:4002", "3_000:3:3_001:9_003"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 메뉴 상품 가격 : `{0}`, 메뉴 상품 수량 : `{1}`, 
                변경할 메뉴 상품 가격 : `{2}`, 총합 금액 : `{3}`
            """)
    void changedProductPrice(final long price, final long quantity, final long changedPrice, final long expected) {
        final UUID menuId = UUID.randomUUID();
        final MenuProducts menuProducts = new MenuProducts(
                List.of(new MenuProduct(1L, price, quantity, menuId, 1L))
        );
        menuProducts.changedProductPrice(1L, changedPrice);

        assertThat(menuProducts.totalAmount()).isEqualTo(expected);
    }
}
