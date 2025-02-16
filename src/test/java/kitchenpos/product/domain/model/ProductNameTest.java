package kitchenpos.product.domain.model;

import kitchenpos.product.domain.exception.ProductNameEmptyException;
import kitchenpos.product.domain.exception.ProductNameValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ProductNameTest {
    @DisplayName("`Product Name`을 입력할 수 있다")
    @Test
    void createProductName() {
        // given
        final String name = "상품";

        // when
        final ProductName productName = ProductName.of(name);

        // then
        assertThat(productName.isSameName(name)).isTrue();
    }

    @DisplayName("`Product Name`에 빈 값이 들어가면 예외가 발생한다")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void createProductNameWithEmptyOrNull(final String name) {
        // when
        final Throwable thrown = catchThrowable(() -> ProductName.of(name));

        // then
        assertThat(thrown).isInstanceOf(ProductNameEmptyException.class);
    }

    @DisplayName("`Product Name` 추가적인 생성규칙에 맞지 않으면 예외가 발생한다")
    @Test
    void createProductNameWithEmptyOrNull() {
        // given
        final String name = "invalid name";
        final ProductNameValidator additionalProductNameValidator = nm -> {
            throw new ProductNameValidationException("검증 실패");
        };

        // when
        final Throwable thrown = catchThrowable(() -> ProductName.of(name, additionalProductNameValidator));

        // then
        assertThat(thrown).isInstanceOf(ProductNameValidationException.class);
    }

    @DisplayName("isSameName 메소드 테스트")
    @ParameterizedTest
    @CsvSource(value = {"상품, 상품, true", "상품, 상품2, false"})
    void testIsSameName(String actualName, String expectedName, boolean expected) {
        // given
        final ProductName productName = ProductName.of(actualName);

        // when
        final boolean isSameName = productName.isSameName(expectedName);

        // then
        assertThat(isSameName).isEqualTo(expected);
    }

    @DisplayName("isSameName 메소드에 null 값을 넣으면 false를 반환한다")
    @Test
    void testIsSameNameWhenNullValue() {
        // given
        final ProductName productName = ProductName.of("상품");

        // when
        final boolean isSameName = productName.isSameName(null);

        // then
        assertThat(isSameName).isFalse();
    }
}