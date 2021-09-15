package kitchenpos.products.application.tobe;

import kitchenpos.products.tobe.domain.ProductName;

import kitchenpos.tobeinfra.TobeFakePurgomalumClient;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.*;

public class TobeProductNameTest {
    private TobePurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new TobeFakePurgomalumClient();
    }

    @DisplayName("비속어 확인")
    @Test
    void purgomalum() {
        assertThatThrownBy(
            () -> new ProductName("욕설", purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈값 확인")
    @NullSource
    @ParameterizedTest
    void nullOfEmpty(final String name) {
        assertThatThrownBy(
                () -> new ProductName(name, purgomalumClient)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성")
    @Test
    void create() {
        ProductName productName = new ProductName("치킨", purgomalumClient);

        assertThat(productName).isNotNull();
    }

    @DisplayName("동등성 확인")
    @Test
    void equalName() {
        ProductName productName1 = new ProductName("치킨", purgomalumClient);
        ProductName productName2 = new ProductName("치킨", purgomalumClient);

        assertThat(productName1).isEqualTo(productName2);
    }
}
