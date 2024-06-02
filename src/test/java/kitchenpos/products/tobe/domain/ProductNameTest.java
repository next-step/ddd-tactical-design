package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        this.purgomalumClient = new FakePurgomalumClient();

    }

    @Test
    @DisplayName("상품명을 생성 할 수 있다.")
    void create() {
        // given
        String name = "상품명";

        // when
        ProductName actual = ProductName.of(name, purgomalumClient);

        // then
        assertNotNull(actual);
        assertEquals(name, actual.name());
    }

    @Test
    @DisplayName("상품명이 비어있으면 IllegalArgumentException 예외가 발생한다.")
    void createWithEmptyName() {
        // given
        String name = "";

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ProductName.of(name, purgomalumClient));
        assertEquals("상품명은 필수값입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("상품명에 비속어가 포함되어 있으면 IllegalArgumentException 예외가 발생한다.")
    void createWithProfanityName() {
        // given
        String name = "비속어";

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ProductName.of(name, purgomalumClient));
        assertEquals("비속어가 포함된 상품명은 등록할 수 없습니다.", exception.getMessage());
    }
}
