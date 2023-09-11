package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    public void init() {
        purgomalumClient = new FakePurgomalumClient();
    }


    @DisplayName("비속어는 상품의 이름이 될수 없다")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void test1(String profanity) {
        assertThatThrownBy(
            () -> new ProductName(profanity, purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("상품명은 비속어가 될수 없습니다");
    }
}