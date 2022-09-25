package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.exception.InvalidProductNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @DisplayName("상품을 생성한다.")
    @Nested
    class CreateTest {

        @DisplayName("상품명에는 비속어가 포함되면 안된다.")
        @Test
        void create() {
            final FakePurgomalumClient fakePurgomalumClient = new FakePurgomalumClient();

            assertThatThrownBy(() -> Product.create("비속어", 10_000L, fakePurgomalumClient))
                    .isInstanceOf(InvalidProductNameException.class);
        }
    }

}
