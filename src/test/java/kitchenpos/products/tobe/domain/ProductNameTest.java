package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.DefaultPurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class ProductNameTest {

    private final PurgomalumClient purgomalumClient = new DefaultPurgomalumClient(new RestTemplateBuilder());

    @Test
    @DisplayName("상품명을 생성한다.")
    void createProductName() {
        // given
        String name = "상품 이름";

        // when
        ProductName displayedName = new ProductName(name, purgomalumClient);

        // then
        assertThat(displayedName).isEqualTo(new ProductName(name, purgomalumClient));
    }

    @Test
    @DisplayName("상품명에 비속어가 존재할 경우 Exception을 발생 시킨다.")
    void createProductNameIfPurifiedNameThrowException() {
        // given
        String name = "bitch";

        // when
        // then
        assertThatThrownBy(() -> new ProductName(name, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
