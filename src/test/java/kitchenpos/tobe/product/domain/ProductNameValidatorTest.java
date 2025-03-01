package kitchenpos.tobe.product.domain;

import kitchenpos.tobe.product.domain.exception.InvalidProductNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductNameValidatorTest {

    private final PurgomalumAgent validPurgomalumAgent = text -> false;
    private final PurgomalumAgent invalidPurgomalumAgent = text -> true;

    @Test
    @DisplayName("상품명을 생성할 수 있다.")
    void createProductName() {
        // given
        final String name = "상품명";
        final ProductName productName = new ProductName(name);
        final ProductNameValidator validator = new ProductNameValidator(validPurgomalumAgent);

        // when
        validator.validate(productName.getName());

        // then
        assertThat(productName.getName()).isEqualTo(name);
    }

    @ParameterizedTest
    @DisplayName("상품명이 null, 빈 문자열이면 예외가 발생한다.")
    @NullAndEmptySource
    void createWithNullName(final String name) {
        // given
        final ProductNameValidator validator = new ProductNameValidator(validPurgomalumAgent);

        // when & then
        assertThatException()
                .isThrownBy(() -> validator.validate(name))
                .isInstanceOf(InvalidProductNameException.class);
    }

    @Test
    @DisplayName("상품명에 비속어가 포함되어 있으면 예외가 발생한다.")
    void createWithProfanity() {
        // given
        final ProductName productName = new ProductName("비속어");
        final ProductNameValidator validator = new ProductNameValidator(invalidPurgomalumAgent);

        // when & then
        assertThatException()
                .isThrownBy(() -> validator.validate(productName.getName()))
                .isInstanceOf(InvalidProductNameException.class);
    }

    @Test
    @DisplayName("같은 상품명을 가진 ProductName 동일하다.")
    void equals() {
        // given
        final String name = "상품명";
        final ProductName productName = new ProductName(name);

        // when
        final ProductName sameProductName = new ProductName(name);

        // then
        assertEquals(productName, sameProductName);
    }

}
