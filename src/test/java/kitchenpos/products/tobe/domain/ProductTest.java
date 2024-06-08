package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        this.purgomalumClient = new FakePurgomalumClient();
    }

    @Test
    @DisplayName("상품을 생성 할 수 있다.")
    void test_1() {
        //given
        ProductName name = ProductName.of("상품1", purgomalumClient);
        ProductPrice price = ProductPrice.of(BigDecimal.valueOf(10000));

        //when
        Product product = Product.create(name, price);

        //then
        assertNotNull(product.getId());
        assertEquals("상품1", product.getName());
        assertEquals(BigDecimal.valueOf(10000), product.getPrice());
    }

    @Test
    @DisplayName("상품의 가격을 변경 할 수 있다.")
    void test_2() {
        //given
        ProductName name = ProductName.of("상품1", purgomalumClient);
        ProductPrice price = ProductPrice.of(BigDecimal.valueOf(10000));
        Product product = Product.create(name, price);

        //when
        product.changePrice(BigDecimal.valueOf(20000));

        //then
        assertEquals(BigDecimal.valueOf(20000), product.getPrice());
    }
}
