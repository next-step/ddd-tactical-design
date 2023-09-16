package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuProductsTest {

    @DisplayName("MenuProducts를 생성할 수 있다.")
    @Test
    void create() {
        MenuProducts menuProducts = new MenuProducts(List.of(
                new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(10000L), 1L),
                new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(20000L), 2L)
        ));
        assertThat(menuProducts.getMenuProducts()).hasSize(2);
    }

    @DisplayName("MenuProducts 생성 시 MenuProduct가 없으면 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void createWithEmptyMenuProducts(List<MenuProduct> menuProducts) {
        assertThatThrownBy(() -> new MenuProducts(menuProducts))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("MenuProducts의 내부에 ProductId 와 일치하는 MenuProduct의 가격을 변경할 수 있다.")
    @Test
    void changeMenuProductPrice() {
        final UUID productId = UUID.randomUUID();
        final MenuProducts menuProducts = new MenuProducts(List.of(
                new MenuProduct(productId, BigDecimal.valueOf(10000L), 1L),
                new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(20000L), 2L)
        ));
        menuProducts.changeMenuProductPrice(productId, BigDecimal.valueOf(30000L));
        assertThat(menuProducts.getMenuProducts()).extracting(MenuProduct::getPrice)
                .containsExactly(BigDecimal.valueOf(30000L), BigDecimal.valueOf(20000L));
    }

    @DisplayName("MenuProducts의 모든 amount를 합한 결과를 반환할 수 있다.")
    @Test
    void totalAmount() {
        final MenuProducts menuProducts = new MenuProducts(List.of(
                new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(10000L), 1L),
                new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(20000L), 2L)
        ));
        assertThat(menuProducts.totalAmount()).isEqualTo(MenuPrice.from(BigDecimal.valueOf(50000L)));
    }
}
