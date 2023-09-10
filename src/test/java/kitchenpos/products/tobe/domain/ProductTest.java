package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("상품테스트")
class ProductTest {

    @DisplayName("상품 생성 성공")
    @Test
    void create() {
        UUID id = UUID.randomUUID();
        Product product = Product.create(id, BigDecimal.valueOf(1000L), "후라이드", new FakePurgomalumClient());
        assertThat(product)
                .isEqualTo(Product.create(id, BigDecimal.valueOf(1000L), "후라이드", new FakePurgomalumClient()));
    }

    @DisplayName("가격변경 성공")
    @Test
    void changePrice() {
        Product product = Product.create(UUID.randomUUID(), BigDecimal.valueOf(1000L), "후라이드", new FakePurgomalumClient());

        product.changePrice(BigDecimal.valueOf(3000L));

        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(3000L));
    }


}
