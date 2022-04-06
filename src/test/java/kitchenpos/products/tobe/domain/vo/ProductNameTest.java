package kitchenpos.products.tobe.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.application.port.out.PurgomalumClient;

class ProductNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("상품의 이름은 비어 있을 수 없다.")
    @ParameterizedTest
    @NullSource
    void can_not_be_null_name(final String name) {
        //given
        //when
        //then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> createProductName(name));
    }

    @DisplayName("상품의 이름은 비속어가 있을 수 없다.")
    @Test
    void can_not_be_contain_purgomalum_name() {
        //given
        //when
        //then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> createProductName("비속어"));
    }

    @DisplayName("hashCode")
    @Test
    void hashCodeTest() {
        //given
        ProductName sameResult_1 = createProductName("치킨");
        ProductName sameResult_2 = createProductName("치킨");
        ProductName other_result_1 = createProductName("전기구이");

        //when
        //then
        assertAll(
            () -> assertThat(sameResult_1.hashCode()).isEqualTo(sameResult_1.hashCode()),
            () -> assertThat(sameResult_1.hashCode()).isEqualTo(sameResult_2.hashCode()),
            () -> assertThat(sameResult_1.hashCode()).isNotEqualTo(other_result_1.hashCode())
        );
    }

    @DisplayName("equals")
    @Test
    void equalsTest() {
        //given
        ProductName sameResult_1 = createProductName("치킨");
        ProductName sameResult_2 = createProductName("치킨");
        ProductName other_result_1 = createProductName("전기구이");

        //when
        //then
        assertAll(
            () -> assertThat(sameResult_1.equals(sameResult_1)).isTrue(),
            () -> assertThat(sameResult_1.equals(sameResult_2)).isTrue(),
            () -> assertThat(sameResult_1.equals(other_result_1)).isFalse()
        );
    }

    private ProductName createProductName(final String name) {
        return new ProductName(name, purgomalumClient);
    }
}