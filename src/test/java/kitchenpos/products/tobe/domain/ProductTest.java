package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.exception.InvalidProductNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @DisplayName("상품을 생성한다.")
    @Nested
    class CreateTest {

        @ParameterizedTest(name = "상품명에는 비어있거나, 비속어가 포함되면 안된다. name={0}")
        @NullSource
        @ValueSource(strings = {"비속어", "욕설"})
        void error_1(final String name) {
            final FakePurgomalumClient fakePurgomalumClient = new FakePurgomalumClient();

            assertThatThrownBy(() -> Product.create(name, 10_000L, fakePurgomalumClient))
                    .isInstanceOf(InvalidProductNameException.class);
        }
    }
}
