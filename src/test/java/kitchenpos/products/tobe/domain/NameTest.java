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

class NameTest {

    private ProductNameProfanities productNameProfanities;

    @BeforeEach
    void setUp() {
        productNameProfanities = new FakeProductNameProfanities();
    }

    @DisplayName("Product의 Name 생성할 수 있다")
    @Test
    void create() {
        final String name = "후라이드";
        final Name productName = Name.from(name, new ProductNamePolicy(productNameProfanities));
        assertAll(
                () -> assertThat(productName).isNotNull(),
                () -> assertThat(name).isEqualTo(productName.getValue())
        );
    }

    @DisplayName("Product의 Name이 null이면 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    void createWithNullName(String name) {
        assertThatThrownBy(() -> Name.from(name, new ProductNamePolicy(productNameProfanities)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Product의 Name에 Profanity를 포함하면 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"비속어포함이름", "중간에욕설포함"})
    void createWithProfanityName(String name) {
        assertThatThrownBy(() -> Name.from(name, new ProductNamePolicy(productNameProfanities)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
