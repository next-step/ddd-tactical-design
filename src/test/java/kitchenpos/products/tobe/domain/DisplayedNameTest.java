package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.products.exception.ProductExceptionMessage.PRODUCT_NAME_CONTAINS_PROFANITY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("상품이름테스트")
class DisplayedNameTest {

    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @DisplayName("상품 이름 생성")
    @Test
    void create() {
        DisplayedName name = DisplayedName.of("이름");
        assertThat(name.getName()).isEqualTo("이름");
    }

    @DisplayName("이름에 비속어가 포함되어 있으면 예외를 반환한다.")
    @Test
    void profanity() {
        assertThatThrownBy(() -> DisplayedName.of("비속어", purgomalumClient))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PRODUCT_NAME_CONTAINS_PROFANITY);
    }

}
