package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductNameTest {
    private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @Test
    @DisplayName("성공")
    void success() {
        //given
        String name = "양념치킨";

        //when
        ProductName productName = new ProductName("양념치킨", purgomalumClient);

        //then
        assertThat(productName.getValue()).isEqualTo(name);
    }

    @Test
    @DisplayName("욕설이 있어서 실패")
    void canNotCreateHaveProfanity() {
        assertThatThrownBy(() -> new ProductName("욕설", purgomalumClient))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ProductName("비속어", purgomalumClient))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ProductName("음식욕설", purgomalumClient))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
