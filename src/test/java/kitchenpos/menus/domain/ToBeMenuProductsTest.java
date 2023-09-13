package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void getSumOfProducts() {
        ToBeMenuProducts menuProducts = new ToBeMenuProducts(List.of(김밥, 돈가스));
        assertThat(menuProducts.getSumOfProducts())
            .isEqualTo(BigDecimal.valueOf(21_000));
    }
}
