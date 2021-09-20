package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.ProductName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TobeProductNameTest {

    @DisplayName("빈값 확인")
    @NullSource
    @ParameterizedTest
    void nullOfEmpty(final String name) {
        assertThatThrownBy(
                () -> new ProductName(name)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성")
    @Test
    void create() {
        ProductName productName = new ProductName("치킨");

        assertThat(productName).isNotNull();
    }

    @DisplayName("동등성 확인")
    @Test
    void equalName() {
        ProductName productName1 = new ProductName("치킨");
        ProductName productName2 = new ProductName("치킨");

        assertThat(productName1).isEqualTo(productName2);
    }
}
