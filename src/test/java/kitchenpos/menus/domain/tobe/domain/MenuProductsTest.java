package kitchenpos.menus.domain.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MenuProductsTest {

    private List<MenuProduct> menuProducts;

    @BeforeEach
    void setUp() {
        menuProducts = Arrays.asList(
                new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(10_000), 1L),
                new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(5_000), 2L)
        );
    }

    @DisplayName("메뉴 상품 목록을 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new MenuProducts(menuProducts))
                .doesNotThrowAnyException();
    }

    @DisplayName("메뉴 상품 목록은 비어있지 않아야한다")
    @ParameterizedTest
    @NullAndEmptySource
    void invalidList(List<MenuProduct> menuProducts) {
        assertThatThrownBy(() -> new MenuProducts(menuProducts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 상품 목록은 빈 값이 아니어야 합니다. 입력 값 : " + menuProducts);
    }

    @DisplayName("메뉴 상품 목록의 합계 금액을 가져온다")
    @Test
    void getTotalAmount() {
        // given
        MenuProducts menuProducts = new MenuProducts(this.menuProducts);

        // when
        BigDecimal totalAmount = menuProducts.getTotalAmount();

        // then
        assertThat(totalAmount).isEqualTo(BigDecimal.valueOf(20_000));
    }
}
