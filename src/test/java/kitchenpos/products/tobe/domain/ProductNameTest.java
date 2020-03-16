package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.exception.WrongProductNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProductNameTest {

    @DisplayName("상품 이름을 입력해야 한다.")
    @Test
    void wrongProductName (){
        assertThatExceptionOfType(WrongProductNameException.class)
            .isThrownBy(() -> new ProductName(null));
    }

    @DisplayName("상품이름은 공백을 제외하고 한 글자 이상이다.")
    @ValueSource(strings = {" ", ""})
    @ParameterizedTest
    void productisNotEmpty (final String name){
        assertThatExceptionOfType(WrongProductNameException.class)
            .isThrownBy(() -> new ProductName(name));
    }

    @DisplayName("상품이름에 공백이 포함되면, 공백을 제거하고 입력한다.")
    @ValueSource(strings = {"t e s t", " test", "test  ", "t    est"})
    @ParameterizedTest
    void prodectNameWithSpace (final String name){
        assertThat(new ProductName(name).valueOf()).isEqualTo("test");
    }
}
