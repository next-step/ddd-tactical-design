package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.menus.domain.tobe.domain.ToBeMenuProduct;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuProducts;

class ToBeMenuProductsTest {
    private ToBeMenuProduct 김밥;
    private ToBeMenuProduct 돈가스;

    @BeforeEach
    void setUp() {
        김밥 = new ToBeMenuProduct(UUID.randomUUID(), 8000L, 1);
        돈가스 = new ToBeMenuProduct(UUID.randomUUID(), 13_000L, 1);
    }

    @DisplayName("menuProduct 일급 컬렉션은 상품이 필수다")
    @Test
    void ToBeMenuProducts() {
        assertThatThrownBy(() -> new ToBeMenuProducts(List.of()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("상품이 없으면 등록할 수 없다.");
    }

    @DisplayName("메뉴에 속한 상품 전체 합계금액 조회")
    @Test
    void sumOfProducts() {
        ToBeMenuProducts menuProducts = new ToBeMenuProducts(List.of(김밥, 돈가스));
        assertThat(menuProducts.sumOfProducts())
            .isEqualTo(BigDecimal.valueOf(21_000));
    }
}
