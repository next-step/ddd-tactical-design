package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.exception.NoSuchMenuProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴 상품 목록 단위 테스트")
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
                        new MenuProduct(1L, firstPrice, firstQuantity, null, 1L),
                        new MenuProduct(2L, secondPrice, secondQuantity, null, 2L)
                )
        );
        assertThat(menuProducts.totalAmount()).isEqualTo(expected);
    }

    @DisplayName("메뉴 상품 목록에 메뉴 상품이 존재하지 않으면 가격을 변경할 수 없다.")
    @CsvSource(value = {"1_000:1:1_001", "2_000:2:2_001", "3_000:3:3_001"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 메뉴 상품 가격 : `{0}`, 메뉴 상품 수량 : `{1}`, 
                변경할 메뉴 상품 가격 : `{2}`
            """)
    void changePriceWithInvalidProduct(final long price, final long quantity, final long changedPrice) {
        final MenuProducts menuProducts = new MenuProducts(
                List.of(new MenuProduct(1L, price, quantity, null, 1L))
        );
        assertThatThrownBy(() -> menuProducts.changeProductPrice(2L, changedPrice))
                .isExactlyInstanceOf(NoSuchMenuProductException.class);
    }

    @DisplayName("메뉴 상품 목록의 가격을 변경할 수 있다.")
    @CsvSource(value = {"1_000:1:1_001:1_001", "2_000:2:2_001:4002", "3_000:3:3_001:9_003"}, delimiter = ':')
    @ParameterizedTest(name = """
                {index}. 메뉴 상품 가격 : `{0}`, 메뉴 상품 수량 : `{1}`, 
                변경할 메뉴 상품 가격 : `{2}`, 총합 금액 : `{3}`
            """)
    void changeProductPrice(final long price, final long quantity, final long changedPrice, final long expected) {
        final MenuProducts menuProducts = new MenuProducts(
                List.of(new MenuProduct(1L, price, quantity, null, 1L))
        );
        menuProducts.changeProductPrice(1L, changedPrice);

        assertThat(menuProducts.totalAmount()).isEqualTo(expected);
    }
}
