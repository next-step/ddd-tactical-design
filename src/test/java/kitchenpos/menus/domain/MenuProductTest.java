package kitchenpos.menus.domain;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.ToBeFixtures;

import kitchenpos.products.tobe.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;

@DisplayName("메뉴 상품 도메인 테스트")
public class MenuProductTest {
    private ToBeFixtures toBeFixtures;

    @BeforeEach
    void setUp() {
        toBeFixtures = new ToBeFixtures();
    }

    @Test
    @DisplayName("메뉴 상품을 생성한다.")
    void create() {
        Product 후라이드 = toBeFixtures.후라이드_20000;
        MenuProduct 메뉴_상품 = new MenuProduct(
                new Random().nextLong(), 후라이드, 10
        );

        Assertions.assertThat(메뉴_상품.getSeq()).isNotNull();
    }

    @Test
    @DisplayName("메뉴 상품의 수량은 0보다 커야 한다.")
    void create_exception_quantity() {
        Product 후라이드 = toBeFixtures.후라이드_20000;
        long 수량 = -1;

        Assertions.assertThatThrownBy(
                () -> new MenuProduct(new Random().nextLong(), 후라이드, 수량)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품 금액 * 수량 금액을 반환한다.")
    void totalPrice() {
        Product 후라이드 = toBeFixtures.후라이드_20000;
        MenuProduct 메뉴_상품 = new MenuProduct(
                new Random().nextLong(), 후라이드, 10
        );

        Assertions.assertThat(메뉴_상품.totalPrice().compareTo(BigDecimal.valueOf(200_000))).isZero();
    }
}
