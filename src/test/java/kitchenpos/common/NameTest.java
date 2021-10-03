package kitchenpos.common;

import kitchenpos.common.domain.model.Name;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> new Name("후라이드", new FakeProfanities()));
    }

    @DisplayName("동등성 검증")
    @Test
    void equals() {
        Name name1 = new Name("후라이드", new FakeProfanities());
        Name name2 = new Name("양념치킨", new FakeProfanities());
        Name name3 = new Name("후라이드", new FakeProfanities());
        Assertions.assertThat(name1.equals(name2))
                .isFalse();
        Assertions.assertThat(name1.equals(name3))
                .isTrue();
    }

    @DisplayName("비속어 포함시 에러 검증")
    @Test
    void profanities() {
        Assertions.assertThatThrownBy(() -> new Name("비속어", new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("null 로 생성시 에러 검증")
    @Test
    void nullValue() {
        Assertions.assertThatThrownBy(() -> new Name(null, new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
