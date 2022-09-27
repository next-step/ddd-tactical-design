package kitchenpos.menus.tobe.domain.vo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.exception.MaximumMenuPriceException;
import kitchenpos.menus.tobe.domain.exception.MinimumMenuPriceException;
import kitchenpos.menus.tobe.domain.exception.NullMenuPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuPriceTest {

    @DisplayName("가격을 등록할 수 있다.")
    @Test
    void create() {
        MenuPrice actual = createMenuPrice(BigDecimal.ZERO);

        assertThat(actual).isEqualTo(createMenuPrice(BigDecimal.ZERO));
        assertThat(actual.hashCode() == createMenuPrice(BigDecimal.ZERO).hashCode())
                .isTrue();
    }

    private MenuPrice createMenuPrice(BigDecimal price) {
        return createMenuPrice(price, createMenuProducts());
    }

    private MenuPrice createMenuPrice(BigDecimal price, MenuProducts menuProducts) {
        return new MenuPrice(price, menuProducts);
    }

    private MenuProducts createMenuProducts() {
        return new MenuProducts(List.of(
                createMenuProduct(),
                createMenuProduct()
        ));
    }

    private MenuProduct createMenuProduct() {
        return new MenuProduct(
                UUID.randomUUID(),
                new MenuProductQuantity(1),
                BigDecimal.ONE
        );
    }

    @DisplayName("메뉴 가격 에러 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("반드시 값이 존재해야 한다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @NullSource
        void error1(BigDecimal price) {
            assertThatExceptionOfType(NullMenuPriceException.class)
                    .isThrownBy(() -> createMenuPrice(price));
        }

        @DisplayName("반드시 0원 이상이어야 한다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = "-1")
        void error2(BigDecimal price) {
            assertThatExceptionOfType(MinimumMenuPriceException.class)
                    .isThrownBy(() -> createMenuPrice(price));
        }

        @DisplayName("메뉴 가격은 제품 금액의 합보다 작거나 같아야 한다.")
        @Test
        void error3() {
            MenuProducts menuProducts = createMenuProducts();
            BigDecimal sum = menuProducts.sum();

            BigDecimal price = sum.add(BigDecimal.ONE);

            assertThatExceptionOfType(MaximumMenuPriceException.class)
                    .isThrownBy(() -> createMenuPrice(price, menuProducts));
        }
    }
}
