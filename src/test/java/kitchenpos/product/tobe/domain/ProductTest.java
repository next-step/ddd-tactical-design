package kitchenpos.product.tobe.domain;

import kitchenpos.common.infra.FakePurgomalumClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class ProductTest {

    @Test
    @DisplayName("정상적인 상품을 생성할 수 있다.")
    void test1() {
        UUID id = UUID.randomUUID();
        String name = "name";
        BigDecimal price = BigDecimal.valueOf(1000);

        Product product = new Product(id, name, price, new FakePurgomalumClient(false));

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getId()).isEqualTo(id);
        Assertions.assertThat(product.getName()).isEqualTo(name);
        Assertions.assertThat(product.getPrice()).isEqualTo(price);
    }

    @Test
    @DisplayName("상품 가격을 변경할 수 있다.")
    void test2() {
        // gjven
        UUID id = UUID.randomUUID();
        Product product = new Product(id, "name", new BigDecimal(1000L), new FakePurgomalumClient(false));
        BigDecimal newPrice = new BigDecimal(2000L);

        // when
        product.changePrice(newPrice);

        // then
        Assertions.assertThat(product.getPrice()).isEqualTo(newPrice);
    }

}
