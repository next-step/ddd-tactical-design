package kitchenpos.products.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductNameTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> new ProductName("후라이드", new FakeProfanities()));
    }

    @DisplayName("동등성 검증")
    @Test
    void equals() {
        ProductName productName1 = new ProductName("후라이드", new FakeProfanities());
        ProductName productName2 = new ProductName("양념치킨", new FakeProfanities());
        ProductName productName3 = new ProductName("후라이드", new FakeProfanities());
        Assertions.assertThat(productName1.equals(productName2))
                .isFalse();
        Assertions.assertThat(productName1.equals(productName3))
                .isTrue();
    }

    @DisplayName("비속어 포함시 에러 검증")
    @Test
    void profanities() {
        Assertions.assertThatThrownBy(() -> new ProductName("비속어", new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("null 로 생성시 에러 검증")
    @Test
    void nullValue() {
        Assertions.assertThatThrownBy(() -> new ProductName(null, new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
