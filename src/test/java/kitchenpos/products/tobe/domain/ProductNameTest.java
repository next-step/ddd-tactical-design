package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.exception.ProductNameNullPointerException;
import kitchenpos.products.exception.ProductNameProfanityException;
import kitchenpos.support.infra.PurgomalumClient;
import kitchenpos.products.infra.ProductProfanityChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("상품 이름")
class ProductNameTest {
    private PurgomalumClient purgomalumClient;
    private ProfanityChecker profanityChecker;

    private static final String 상품_이름 = "상품 이름";

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
        profanityChecker = new ProductProfanityChecker(purgomalumClient);
    }

    @DisplayName("[성공] 상품 이름을 생성한다.")
    @Test
    void success() {
        ProductName actual = ProductName.from(상품_이름, profanityChecker);

        assertThat(actual).isEqualTo(new ProductName(상품_이름, profanityChecker));
    }

    @DisplayName("[실패] 상품 이름은 비워둘 수 없다.")
    @NullSource
    @ParameterizedTest
    void fail1(String name) {
        assertThatThrownBy(() -> ProductName.from(name, profanityChecker))
                .isInstanceOf(ProductNameNullPointerException.class);
    }

    @DisplayName("[실패] 상품 이름은 비속어를 포함할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void fail2(String name) {
        assertThatThrownBy(() -> ProductName.from(name, profanityChecker))
                .isInstanceOf(ProductNameProfanityException.class);
    }
}
