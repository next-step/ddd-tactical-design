package kitchenpos.menus.domain;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.ToBeFixtures;

import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.Price;
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

        Assertions.assertThat(메뉴_상품.getSeq()).isNotNull();
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

            Assertions.assertThat(메뉴_상품.totalPrice().compareTo(BigDecimal.valueOf(200_000))).isZero();
        }

        @Test
        @DisplayName("메뉴 상품의 금액을 반환한다.")
        void totalPrice2() {
            Price 십만원 = new Price(BigDecimal.valueOf(100_000));
            MenuProduct 메뉴_상품 = new MenuProduct(
                    random.nextLong(), 5, 후라이드.getId(), 후라이드.getPrice()
            );

            Assertions.assertThat(메뉴_상품.totalPrice().equals(십만원.getPrice())).isTrue();
        }

        @Test
        @DisplayName("메뉴 상품들의 총 금액을 반환한다.")
        void calculateTotalPrice2() {
            Product 양념_치킨 = toBeFixtures.양념치킨_20000;
            MenuProduct 메뉴_후라이드 = new MenuProduct(
                    random.nextLong(), 3, 후라이드.getId(), 후라이드.getPrice()
            );
            MenuProduct 메뉴_양념치킨 = new MenuProduct(
                    random.nextLong(), 3, 양념_치킨.getId(), 양념_치킨.getPrice()
            );
            List<MenuProduct> 메뉴_치킨_목록 = List.of(메뉴_후라이드, 메뉴_양념치킨);
            MenuProducts menuProducts = new MenuProducts(메뉴_치킨_목록);

            BigDecimal 총_금액 = menuProducts.price();

            Assertions.assertThat(총_금액.compareTo(BigDecimal.valueOf(120_000))).isZero();
        }

        @Test
        @DisplayName("메뉴 상품의 가격과 메뉴 가격을 비교한다.")
        void isLessThenMenuPrice() {
            List<MenuProduct> 메뉴_상품_목록 = List.of(
                    ToBeFixtures.menuProductOf(1, BigDecimal.valueOf(9_000))
            );
            MenuProducts menuProducts = new MenuProducts(메뉴_상품_목록);
            BigDecimal 메뉴_가격 = BigDecimal.valueOf(10_000);

            Assertions.assertThat(menuProducts.isLessThenMenuPrice(메뉴_가격)).isTrue();
        }
    }
}
