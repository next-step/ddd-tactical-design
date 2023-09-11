package kitchenpos.products.application;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {
    
    private FakePurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("상품생성")
    @Test
    void 상품생성() {
        // when
        Product product = product("치킨", 15_000L, purgomalumClient);
        // then
        assertThat(product.getId()).isNotNull();
    }

    @DisplayName("상품의 이름에는 비속어가 포함될 수 없다.")
    @Test
    void 상품생성_이름_욕설포함() {
        // when then
        assertThatThrownBy(() -> product("욕설치킨", 15_000L, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격은 0원 이상이어야 한다.")
    @Test
    void 상품생성_금액_음수() {
        // when then
        assertThatThrownBy(() -> product("치킨", -15_000L, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품가격변경")
    @Test
    void 상품가격변경() {
        // given
        Product product = product("치킨", 15_000L, purgomalumClient);

        // when
        product.changePrice(new BigDecimal(12_000L));

        // then
        assertThat(product.getPrice()).isEqualTo(new BigDecimal(12_000L));
    }

    @DisplayName("상품의 가격이 0원 이상이 아닐경우 변경할 수 없다.")
    @Test
    void 상품가격변경_음수가격() {
        // given
        Product product = product("치킨", 15_000L, purgomalumClient);

        // when then
        assertThatThrownBy(() -> product.changePrice(new BigDecimal(-12_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
