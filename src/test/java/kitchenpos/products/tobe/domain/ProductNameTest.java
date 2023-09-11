package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.common.NamePolicy;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {

    private NamePolicy namePolicy;

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
        namePolicy = new NamePolicy(purgomalumClient);
    }

    @DisplayName("상품 이름 은 존재해야 한다.")
    @Test
    void createProductName() {
        String name = "짜장면";

        ProductName test = new ProductName(name, namePolicy);

        assertThat(test).isNotNull();
    }

    @DisplayName("상품의 이름이 null 일 경우 예외를 발생한다.")
    @ParameterizedTest
    @NullSource
    void exceptionProductNameRequired(String name) {
        assertThatThrownBy(() -> new ProductName(name, namePolicy))
                .isInstanceOf(IllegalArgumentException.class);
    }

}