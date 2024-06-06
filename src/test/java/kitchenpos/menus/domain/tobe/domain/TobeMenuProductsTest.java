package kitchenpos.menus.domain.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TobeMenuProductsTest {

    @DisplayName("")
    @Test
    void case_1() {
        // given
        var menuProduct1 = createTobeMenuProduct(10_000, 2);
        var menuProduct2 = createTobeMenuProduct(13_000, 1);
        var menuProduct3 = createTobeMenuProduct(12_000, 1);
        var menuProducts = createTobeMenuProducts(menuProduct1, menuProduct2, menuProduct3);

        // when
        int totalPrice = menuProducts.getTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(45_000);

    }

    private TobeMenuProduct createTobeMenuProduct(int price, int quantity) {
        return new TobeMenuProduct(price, quantity, UUID.randomUUID());
    }

    private TobeMenuProducts createTobeMenuProducts(TobeMenuProduct... tobeMenuProducts) {
        return TobeMenuProducts.of(List.of(tobeMenuProducts));
    }
}