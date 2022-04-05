package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class ProductNameTest {

    @Test
    void create() {
        assertThatCode(() -> new ProductName("짜장면"))
                .doesNotThrowAnyException();
    }
}