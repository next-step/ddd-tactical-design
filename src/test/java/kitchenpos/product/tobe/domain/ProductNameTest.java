package kitchenpos.product.tobe.domain;

import kitchenpos.common.tobe.Profanities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductNameTest {
    @DisplayName("상품명에 비속어가 포함되어 있으면 예외가 발생한다")
    @Test
    void create_with_profanity() {
        // given
        String profanityName = "비속어포함상품";
        Profanities profanities = text -> text.equals(profanityName);

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ProductName(profanityName, profanities)
        );
        assertThat(exception.getMessage()).isEqualTo("비속어가 포함되어 있습니다.");
    }

    @DisplayName("상품명은 null이거나 빈 문자열일 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void create_with_invalid_name(String invalidName) {
        // given
        Profanities profanities = text -> false;

        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ProductName(invalidName, profanities)
        );
        assertThat(exception.getMessage()).isEqualTo("상품명은 필수값입니다.");
    }

    @DisplayName("상품명이 동일한 경우 equals가 true를 반환한다")
    @Test
    void equals_with_same_name() {
        // given
        String name = "치킨";
        ProductName productName1 = new ProductName(name);
        ProductName productName2 = new ProductName(name);

        // when & then
        assertThat(productName1).isEqualTo(productName2);
        assertThat(productName1.hashCode()).isEqualTo(productName2.hashCode());
    }

    @DisplayName("상품명이 다른 경우 equals가 false를 반환한다")
    @Test
    void equals_with_different_name() {
        // given
        ProductName productName1 = new ProductName("치킨");
        ProductName productName2 = new ProductName("피자");

        // when & then
        assertThat(productName1).isNotEqualTo(productName2);
    }

    @DisplayName("정상적인 상품명으로 객체를 생성할 수 있다")
    @Test
    void create_with_valid_name() {
        // given
        String validName = "치킨";
        Profanities profanities = text -> false;

        // when & then
        ProductName productName = new ProductName(validName, profanities);

        // 추가적인 검증이 필요하다면 getter를 만들어서 검증할 수 있습니다
    }
}