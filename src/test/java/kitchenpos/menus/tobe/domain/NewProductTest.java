package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("상품테스트")
class NewProductTest {


    @DisplayName("상품 생성 성공")
    @Test
    void create() {
        UUID id = UUID.randomUUID();
        NewProduct newProduct = NewProduct.create(id, Price.of(BigDecimal.valueOf(1_000L)));

        assertThat(newProduct).isEqualTo(NewProduct.create(id, Price.of(BigDecimal.valueOf(1_000L))));
    }

}
