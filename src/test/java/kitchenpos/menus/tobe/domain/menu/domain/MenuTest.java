package kitchenpos.menus.tobe.domain.menu.domain;

import kitchenpos.common.tobe.domain.Price;
import kitchenpos.menus.tobe.domain.menu.ProductPriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.Arrays;

import static kitchenpos.menus.TobeFixtures.menuProduct;
import static kitchenpos.menus.TobeFixtures.twoFriedChickens;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @DisplayName("정상적으로 생성되는 것 테스트.")
    @Test
    void create() {
        Menu result = twoFriedChickens();
        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getName()).isEqualTo("후라이드+후라이드"),
                () -> assertThat(result.getPrice()).isEqualTo(new Price(BigDecimal.valueOf(19_000L))),
                () -> assertThat(result.getMenuGroupId()).isEqualTo(1L),
                () -> assertThat(result.getMenuProducts()).isEqualTo(Arrays.asList(menuProduct()))
        );
    }

    @DisplayName("메뉴는 하나의 그룹에 속해야 한다.")
    @ParameterizedTest
    @NullSource
    void createFail(final Long menuGroupId) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> twoFriedChickens(menuGroupId));
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void validateMenuPrice() {
        Menu result = twoFriedChickens(BigDecimal.valueOf(33_000L));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> result.validateMenuPrice(Arrays.asList(new ProductPriceResponse(1L, BigDecimal.valueOf(16_000L)))));
    }
}