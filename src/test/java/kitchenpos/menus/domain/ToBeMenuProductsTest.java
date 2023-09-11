package kitchenpos.menus.domain;

import static kitchenpos.menus.domain.MenuFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kitchenpos.menus.domain.tobe.domain.ToBeMenuProduct;
import kitchenpos.menus.domain.tobe.domain.ToBeMenuProducts;
import kitchenpos.products.domain.tobe.domain.Price;
import kitchenpos.products.domain.tobe.domain.ToBeProduct;

class ToBeMenuProductsTest {
    private ToBeProduct 김밥;
    private ToBeProduct 돈가스;

    @BeforeEach
    void setUp() {
        김밥 = createProduct("김밥", 8000L);
        돈가스 = createProduct("돈가스", 13_000L);
    }

    @Test
    void getSumOfProducts() {
        ToBeMenuProduct 김밥_메뉴_상품 = createMenuProduct(김밥, 2);
        ToBeMenuProduct 돈가스_메뉴_상품 = createMenuProduct(돈가스, 3);
        ToBeMenuProducts menuProducts = new ToBeMenuProducts(List.of(김밥_메뉴_상품, 돈가스_메뉴_상품));

        assertThat(menuProducts.getSumOfProducts())
            .isEqualTo(Price.of(55_000L));
    }
}
