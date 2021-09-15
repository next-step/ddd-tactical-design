package kitchenpos.products.tobe.domain;

import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Profanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductTest {
    private Profanities profanities;

    @BeforeEach
    void setUp() {
        profanities = new FakePurgomalumClient();
    }

    @DisplayName("상품을 생성할 수 있다.")
    @Test
    void create() {
        //given
        DisplayedName name = new DisplayedName("후라이드", profanities);
        Price price = new Price(BigDecimal.valueOf(16_000L));

        //when
        Product product = new Product(name, price);

        //then
        assertAll(
                () -> assertThat(product).isNotNull(),
                () -> assertThat(product.getDisplayedName().getName()).isEqualTo(name.getName()),
                () -> assertThat(product.getPrice().getPrice()).isEqualTo(price.getPrice())
        );
    }

}
