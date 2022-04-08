package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ProductNameTest {

    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();


    @DisplayName("상품의 이름이 정상적으로 등록된다.")
    @Test
    void productNameSuccess() {
        // given
        final String name = "간장치킨";

        // when
        final ProductName productName = new ProductName(name, purgomalumClient);

        // then
        assertThat(productName.value()).isEqualTo(name);
    }

    @DisplayName("상품의 이름은 비어있거나 비속어가 포함될 수 없다.")
    @NullAndEmptySource
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void productNameEmptyOrProfanity(String name) {

        // when - then
        assertThatThrownBy(() -> new ProductName(name, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class);

    }
}
