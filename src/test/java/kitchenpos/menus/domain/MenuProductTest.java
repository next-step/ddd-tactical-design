package kitchenpos.menus.domain;

import kitchenpos.ToBeFixtures;

import kitchenpos.menus.tobe.domain.entity.MenuProducts;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.menus.tobe.domain.common.Price;
import kitchenpos.products.tobe.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@DisplayName("메뉴 상품 도메인 테스트")
public class MenuProductTest {
    private ToBeFixtures toBeFixtures;
    private Product 후라이드;
    private Random random = new Random();

    @BeforeEach
    void setUp() {
        toBeFixtures = new ToBeFixtures();
        후라이드 = toBeFixtures.후라이드_20000;
    }

    @Test
    @DisplayName("메뉴 상품을 생성한다.")
    void create() {
        MenuProduct 메뉴_상품 = ToBeFixtures.menuProductOf(1L, 후라이드.getPrice());

        Assertions.assertThat(메뉴_상품.getSeq()).isNotNull();
    }

    @Test
    @DisplayName("가격을 가지는 메뉴 상품을 생성한다.")
    void create_price() {
        MenuProduct 메뉴_상품 = new MenuProduct(
                random.nextLong(), 10, 후라이드.getId(), 후라이드.getPrice()
        );
        Price 메뉴_상품_가격 = 메뉴_상품.totalPrice();

        Assertions.assertThat(메뉴_상품_가격.getPrice()).isEqualTo(BigDecimal.valueOf(200_000));
    }

    @Test
    @DisplayName("메뉴 상품의 수량은 0보다 커야 한다.")
    void create_exception_quantity() {
        long 수량 = -1;

        Assertions.assertThatThrownBy(
                () -> ToBeFixtures.menuProductOf(수량, 후라이드.getPrice())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("메뉴 상품 가격 관련 테스트")
    class PriceTest {
        @Test
        @DisplayName("상품 금액 * 수량 금액을 반환한다.")
        void totalPrice() {
            MenuProduct 메뉴_상품 = ToBeFixtures.menuProductOf(10, BigDecimal.valueOf(20_000));

            int result = 메뉴_상품.totalPrice().getPrice().compareTo(BigDecimal.valueOf(200_000));
            Assertions.assertThat(result).isZero();
        }

        @Test
        @DisplayName("메뉴 상품의 금액을 반환한다.")
        void totalPrice2() {
            Price 십만원 = new Price(BigDecimal.valueOf(100_000));
            MenuProduct 메뉴_상품 = new MenuProduct(
                    random.nextLong(), 5, 후라이드.getId(), 후라이드.getPrice()
            );

            boolean result = 메뉴_상품.totalPrice().equals(십만원);
            Assertions.assertThat(result).isTrue();
        }

        @Test
        @DisplayName("메뉴 상품의 가격과 메뉴 가격을 비교한다.")
        void isLessThenMenuPrice() {
            List<MenuProduct> 메뉴_상품_목록 = List.of(
                    ToBeFixtures.menuProductOf(1, BigDecimal.valueOf(9_000))
            );
            MenuProducts menuProducts = new MenuProducts(메뉴_상품_목록);
            Price 메뉴_가격 = new Price(BigDecimal.valueOf(10_000));

            Assertions.assertThat(menuProducts.isLessThenPrice(메뉴_가격)).isTrue();
        }
    }
}
