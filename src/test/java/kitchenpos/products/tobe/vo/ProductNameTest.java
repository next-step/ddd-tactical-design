package kitchenpos.products.tobe.vo;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("상품 이름")
class ProductNameTest {

    @DisplayName("이름 생성")
    @Test
    void createProductName() {
        ProductName name = new ProductName("후라이드");

        assertThat(name.getValue()).isEqualTo("후라이드");
    }

    @DisplayName("이름이 null 또는 빈값이면 에러")
    @NullAndEmptySource
    @ParameterizedTest
    void createProductNameNull(String value) {
        assertThatThrownBy(() -> new ProductName(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름에 비속어가 포함된다면 에러")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void createProductNameSlang(String value) {
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();

        assertThatThrownBy(() -> new ProductName(value, purgomalumClient)).isInstanceOf(IllegalArgumentException.class);
    }
}
