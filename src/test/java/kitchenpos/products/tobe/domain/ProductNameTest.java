package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {

    private static final PurgomalumClient PURGOMALUM_CLIENT = new FakePurgomalumClient();

    @DisplayName("상품 이름 생성")
    @Test
    void construct() {
        // given when
        ProductName productName = new ProductName("후라이드치킨", PURGOMALUM_CLIENT);

        // then
        assertThat(productName).isEqualTo(new ProductName("후라이드치킨", PURGOMALUM_CLIENT));
    }

    @DisplayName("상품 이름은 필수 항목이다.")
    @ParameterizedTest
    @NullAndEmptySource
    void nameIsMandatory(String name) {
        // given when then
        assertThatThrownBy(() -> new ProductName(name, PURGOMALUM_CLIENT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품 이름은 필수항목입니다. 상품이름을 확인해주세요.");
    }

    @DisplayName("상품 이름에는 비속어가 포함될 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void containsProfanity(String name) {
        // given when then
        assertThatThrownBy(() -> new ProductName(name, PURGOMALUM_CLIENT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 이름에는 비속어가 포함될 수 없습니다. 상품이름을 확인해주세요.");
    }
}
