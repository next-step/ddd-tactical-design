package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakeProductNameProfanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductNameTest {

    private ProductNameProfanities productNameProfanities;

    @BeforeEach
    void setUp() {
        productNameProfanities = new FakeProductNameProfanities();
    }

    @DisplayName("상품 이름 생성")
    @Test
    void create() {
        final String name = "후라이드";
        final ProductName productName = ProductName.from(name, new ProductNamePolicy(productNameProfanities));
        assertAll(
                () -> assertThat(productName).isNotNull(),
                () -> assertThat(name).isEqualTo(productName.getValue())
        );
    }

    @DisplayName("상품 이름이 null이면 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    void createWithNullName(String name) {
        assertThatThrownBy(() -> ProductName.from(name, new ProductNamePolicy(productNameProfanities)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 이름에 비속어를 포함하면 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"비속어포함이름", "중간에욕설포함"})
    void createWithProfanityName(String name) {
        assertThatThrownBy(() -> ProductName.from(name, new ProductNamePolicy(productNameProfanities)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
