package kitchenpos.menus.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

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
        final MenuProducts menuProducts = new MenuProducts(
                List.of(
                        new MenuProduct(1L, firstPrice, firstQuantity, 1L),
                        new MenuProduct(2L, secondPrice, secondQuantity, 2L)
                )
        );
        assertThat(menuProducts.totalAmount()).isEqualTo(expected);
    }
}
