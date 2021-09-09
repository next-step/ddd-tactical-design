package kitchenpos.products.tobe.domain;

import kitchenpos.common.infra.FakeProfanities;
import kitchenpos.common.infra.Profanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProductNameTest {
    private Profanities profanities;

    @BeforeEach
    void setUp() {
        profanities = new FakeProfanities();
    }

    @DisplayName("상품 이름은 비어있을 수 없다.")
    @Test
    void emptyName() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ProductName(null, profanities))
                .withMessage("상품 이름은 필수값입니다.");
    }

    @DisplayName("적절하지 않은 상품 이름은 validation 에 실패한다.")
    @Test
    void profanityName() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ProductName("욕설", profanities))
                .withMessage("적절하지 않은 상품 이름입니다.");
    }

    @DisplayName("상품 이름이 같으면, 같아야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"치킨", "피자"})
    void equalPrice(final String name) {
        assertThat(new ProductName(name, profanities))
                .isEqualTo(new ProductName(name, profanities));
    }
}
