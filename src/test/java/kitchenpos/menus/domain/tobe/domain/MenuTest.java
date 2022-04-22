package kitchenpos.menus.domain.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MenuTest {
    private List<MenuProduct> menuProducts;

    @BeforeEach
    void setUp() {
        menuProducts = Arrays.asList(
                new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(10_000), 1L),
                new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(5_000), 2L)
        );
    }

    @DisplayName("이름, 가격, 메뉴 그룹 이름, 상품 목록으로 메뉴를 생성한다")
    @ParameterizedTest
    @ValueSource(strings = {"15000", "20000"})
    void create(BigDecimal price) {
        assertThatCode(() ->
                new Menu("탕수육 세트", price, "요리 세트", menuProducts))
                .doesNotThrowAnyException();
    }

    @DisplayName("메뉴를 생성할 때 가격은 메뉴 상품 목록 금액의 합보다 적거나 같아야 한다")
    @Test
    void createLessThanOrEqualPrice() {
        assertThatThrownBy(() -> new Menu("탕수육 세트", BigDecimal.valueOf(21_000), "요리 세트", menuProducts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴의 가격은 메뉴 상품 목록 금액의 합보다 적거나 같아야 합니다.");
    }

    @DisplayName("메뉴의 가격을 변경한다")
    @Test
    void changePrice() {
        // given
        Menu menu = new Menu("탕수육 세트", BigDecimal.valueOf(15_000), "요리 세트", menuProducts);

        // when
        menu.changePrice(BigDecimal.valueOf(10_000));

        // then
        assertThat(menu.getPrice()).isEqualTo(new MenuPrice(10_000));
    }

    @DisplayName("메뉴의 가격을 변경할 때 메뉴 상품 목록 금액의 합보다 적거나 같아야 한다")
    @Test
    void changeLessThanOrEqualPrice() {
        // given
        Menu menu = new Menu("탕수육 세트", BigDecimal.valueOf(15_000), "요리 세트", menuProducts);

        // when
        menu.changePrice(BigDecimal.valueOf(21_000));

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }
}
