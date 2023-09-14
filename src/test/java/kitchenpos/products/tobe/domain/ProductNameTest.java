package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LegacyProductNameTest {

    private FakePurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("상품명 생성 성공")
    @Test
    void CreatingProductName_is_success() {
        //given
        String requestProductName = "치킨";

        //when
        ProductName productName = new ProductName(requestProductName, purgomalumClient);

        //then
        assertThat(productName.getProductName()).isEqualTo(requestProductName);
    }
    @DisplayName("상품 명은 필수로 존재해야 한다.")
    @ParameterizedTest
    @NullSource
    void productName_must_exist(String name) {
        //when & then
        assertThatThrownBy(() -> new ProductName(name, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품 이름 값이 존재하지 않습니다.");
    }

    @DisplayName("상품 명에 비속어가 존재하면 안된다.")
    @ParameterizedTest
    @ValueSource(strings = {"욕설", "비속어"})
    void productName_must_not_contains_profanity(String productName) {
        //when & then
        assertThatThrownBy(() -> new ProductName(productName, purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품 이름에 비속어가 존재합니다. product Name : " + productName);
    }
}