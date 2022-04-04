package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private static final PurgomalumClient PURGOMALUM_CLIENT = new FakePurgomalumClient();

    @DisplayName("상품 생성")
    @Test
    void construct() {
        // given when then
        assertThatCode(() -> new Product("후라이드치킨", 16000L, PURGOMALUM_CLIENT))
                .doesNotThrowAnyException();
    }

    @DisplayName("상품 이름은 필수 입력 항목이다.")
    @ParameterizedTest
    @NullAndEmptySource
    void nameIsMandatory(String name) {
        // given when then
        assertThatThrownBy(() -> new Product(name, 16000L, PURGOMALUM_CLIENT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품 이름은 필수항목입니다. 상품이름을 확인해주세요.");
    }

    @DisplayName("상품 이름에는 비속어가 포함될 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void nameContatinsProfanity(String name) {
        // given when then
        assertThatThrownBy(() -> new Product(name, 16000L, PURGOMALUM_CLIENT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 이름에는 비속어가 포함될 수 없습니다. 상품이름을 확인해주세요.");
    }

    @DisplayName("상품의 가격은 0 원 이상이어야 한다.")
    @Test
    void negativePrice() {
        // given when then
        assertThatThrownBy(() -> new Product("후라이드치킨", -1L, PURGOMALUM_CLIENT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 가격은 0 원 이상이어야 합니다. 상품가격을 확인해 주세요.");
    }

    @DisplayName("상품가격변경 - 상품의 가격은 0 원 이상이어야 한다.")
    @Test
    void changePrice() {
        // given
        Product product = new Product("후라이드치킨", 18000L, PURGOMALUM_CLIENT);

        // when then
        assertThatThrownBy(() -> product.changePrice(-18000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품의 가격은 0 원 이상이어야 합니다. 상품가격을 확인해 주세요.");
    }
}
