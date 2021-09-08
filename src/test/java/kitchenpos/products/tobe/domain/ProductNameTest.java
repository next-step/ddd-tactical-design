package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProductNameTest {

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"욕설","비속어"})
    public void create(String name) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ProductName(name, new FakePurgomalumClient()));
    }
}
